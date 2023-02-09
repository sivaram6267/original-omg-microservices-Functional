package com.lancesoft.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lancesoft.customexception.CustomException;
import com.lancesoft.customexception.RegistrationCustomException;
import com.lancesoft.dao.MyCartListRepo;
import com.lancesoft.dao.MyCartRepo;
import com.lancesoft.dto.AddToCartDto;
import com.lancesoft.entity.MyCart;
import com.lancesoft.entity.MyCartList;
import com.lancesoft.entity.ProductsEntity;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.feign.ProductsProxy;
import com.lancesoft.feign.RegistartionProxy;

@Service
public class UserDashboardServiceImpl implements UserDashboardService {

	@Autowired
	MyCartRepo cartRepo;

	@Autowired
	ProductsProxy productsProxy;

	@Autowired
	RegistartionProxy registartionProxy;

	@Autowired
	MyCartListRepo myCartListRepo;

	Logger logger = Logger.getLogger(UserDashboardServiceImpl.class);

	// deleting the item from cart
	
	@Override
	public String deleteCartItem(String cartId) {
		try {
			MyCart myCart=cartRepo.findByCartId(cartId);
			cartRepo.delete(myCart);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("error occured", e.getLocalizedMessage());
		}
		return "deleted";
	}

	// adds the items to cart
	@Override
	public ResponseEntity<?> addToCart(AddToCartDto addToCartDto, String userName) {

		logger.info("add to cart method started");

		try {

			ProductsEntity productsentity = productsProxy.getProdById(addToCartDto.getProdId());
			addToCartDto.setUnit(productsentity.getUnit());
			List<MyCart> cartL = cartRepo.findByUserId(userName);
			for (MyCart myCart : cartL) {

//				if (myCart.getProductsEntity().equals(productsentity) && myCart.getUserId().equals(userName))
//					throw new CustomException("Product Already added to cart", null);
				if (myCart.getProductsId().equals(productsentity.getProdId()) && myCart.getUserId().equals(userName)) {
					myCart.setItemCount(myCart.getItemCount() + 1);
					throw new CustomException("product already in cart", null);
				}

			}
			if (productsentity.getStatus().equals("Not Available")) {
				System.err.println("not availble");
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			} else {

				MyCart cart = new MyCart();
				if (addToCartDto.getUnit().toLowerCase().equals("kg")) {
					cart.setItemPrice(addToCartDto.getQty() * productsentity.getPrice());
				}

				if (addToCartDto.getUnit().toLowerCase().equals("grams")) {
					cart.setItemPrice((addToCartDto.getQty() / 1000) * productsentity.getPrice());

				}
				if (addToCartDto.getUnit().equals("Litre")) {
					cart.setItemPrice((addToCartDto.getQty() / 1000) * productsentity.getPrice());
				}

				if (addToCartDto.getUnit().equals("ML")) {

					cart.setItemPrice((addToCartDto.getQty() / 1000) * productsentity.getPrice());
				}
				if (addToCartDto.getUnit().equals("Piece")) {
					cart.setItemPrice(addToCartDto.getQty() * productsentity.getPrice());
				}

				cart.setUnit(addToCartDto.getUnit());

				cart.setProductsId(productsentity.getProdId());
				cart.setProductsEntity(productsentity);
				cart.setQty(addToCartDto.getQty());
				RegistrationEntity registrationEntity = registartionProxy.findByUserName(userName);
				cart.setUserId(registrationEntity.getUserName());

//				MyCart myCartFromRepo= cartRepo.findByUserId(userName).get(0);

				System.out.println(cart);

				cartRepo.save(cart);
				MyCart cart1 = cartRepo.findByProductsId(cart.getProductsId());
				return new ResponseEntity<>(cart1, HttpStatus.ACCEPTED);

			}
		} catch (RegistrationCustomException e) {
			e.printStackTrace();
			throw new RegistrationCustomException(e.getMessage());
		} catch (CustomException customException) {
			// customException.printStackTrace();
			throw new RegistrationCustomException(customException.getMessage());
		} catch (Exception Exception) {
			Exception.printStackTrace();
			throw new RegistrationCustomException(Exception.getMessage());
		}
	}

	// fetches the all the cart items from the database

	public MyCartList myCartList(MyCartList myCartList, String userName) {

		try {
			List<MyCart> cartList = cartRepo.findByUserId(userName);
			if (cartList.isEmpty()) {
				System.out.println("Your Cart is empty , Please add to cart");
			}
			double totalCartPrice = 0;
			for (MyCart myCart : cartList) {
				totalCartPrice = totalCartPrice + myCart.getItemPrice();
				myCart.setProductsEntity(productsProxy.getProdById(myCart.getProductsId()));
			}

			if (myCartListRepo.existsByUserName(userName)) {
				MyCartList mycartList = myCartListRepo.findByUserName(userName);
				String myCartListId = mycartList.getCartListId();
				// myCartListDao.delete(mycartList);
				myCartList.setCartListId(myCartListId);
			}

			myCartList.setUserName(userName);
			myCartList.setMyCartItems(cartList);
			myCartList.setTotalCost(totalCartPrice);
			myCartList.setTotalItems(cartList.size());

			return myCartListRepo.save(myCartList);
		} catch (Exception e) {
			throw new CustomException("error occured", e.getLocalizedMessage());
		}
	}

	// update the items in the cart
	@Override
	public MyCartList updateCart(String cartId, Double qty, MyCartList cartList, String userName) {
		try {
			MyCart cart = cartRepo.findByCartId(cartId);
			cart.setQty(qty);
			return this.myCartList(cartList, userName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("error code", e.getLocalizedMessage());
		}
	}

	@Override
	public MyCartList findByCartListId(String cartId) {

		return myCartListRepo.findByCartListId(cartId);
	}

	@Override
	public MyCartList findByCartListByUserName(String userName) {
		return myCartListRepo.findByUserName(userName);
	}

	@Override
	public String deleteMyCartList(String userName) {
		try {
			MyCartList cartList = myCartListRepo.findByUserName(userName);
			myCartListRepo.delete(cartList);
			return "deleted";
		} catch (Exception e) {
			throw new CustomException("Your cart is empty", "Your cart is empty");
		}
	}

}