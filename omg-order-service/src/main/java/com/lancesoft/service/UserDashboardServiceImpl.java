package com.lancesoft.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancesoft.customexception.CustomException;
import com.lancesoft.dao.OrderedItemsRepo;
import com.lancesoft.dao.OrdersRepo;
import com.lancesoft.entity.AddressEntity;
import com.lancesoft.entity.Authorities;
import com.lancesoft.entity.Inventory;
import com.lancesoft.entity.MyCart;
import com.lancesoft.entity.MyCartList;
import com.lancesoft.entity.OrderedItems;
import com.lancesoft.entity.OrdersEntity;
import com.lancesoft.entity.ProductsEntity;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.feign.InventoryProxy;
import com.lancesoft.feign.MyCartProxy;
import com.lancesoft.feign.ProductsProxy;
import com.lancesoft.feign.ProfileProxy;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Header;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;


@Service
public class UserDashboardServiceImpl implements UserDashboardService {

	

//	@Autowired
//	private CategoryRepo categoryRepo;

	@Autowired
	private ProfileProxy registrationRepo;

	@Autowired
	private ProductsProxy productsRepo;
	
	@Autowired
	private MyCartProxy cartRepo;

//	@Autowired
//	private MyCartListRepo myCartListRepo;
	
	@Autowired
	OrdersRepo ordersRepo;

	

	@Autowired
	private InventoryProxy inventoryRepo;

	@Autowired
	private OrderedItemsRepo orderedItemsRepo;
	
	@Autowired
	ProductsProxy productsProxy;
	
//	@Autowired
//	AuthoritiesRepo authoritiesRepo;

	

	public OrdersEntity payWithCod(String userName) {
		try {
			
			OrdersEntity ordersEntity = new OrdersEntity();

			// inventories
			int i = 0;
			MyCart myCartItems = new MyCart();
			MyCartList totalCartList = cartRepo.findByCartListByUserName(userName);
			

			
			myCartItems.setCartId(totalCartList.getCartListId());
			List<Inventory> addinvenInventories = new ArrayList<>();
			for (MyCart cart : totalCartList.getMyCartItems()) {
				ProductsEntity productsEntity1=productsProxy.getProdById(cart.getProductsId());
				
				Inventory inventory = inventoryRepo.findByProductName(productsEntity1.getProdName());

				if (inventory.getQuantity() <= inventory.getLowerLimit()) {

					Authorities authorities = registrationRepo.findByRole("Admin");
					List<RegistrationEntity> adminList = registrationRepo.findByAuthorities(authorities);
					for (RegistrationEntity registrationEntity : adminList) {
//						inventorySmsService.sendMessage(registrationEntity.getPhoneNumber(),
//								cart.getProductsEntity().getProdName());
//						inventoryMailService.sendMail(registrationEntity.getEmail(),
//								cart.getProductsEntity().getProdName());
					}
					throw new CustomException("inventory", "inventory quantity is less");
				}
				///// profit & loss

				if (Long.valueOf(productsEntity1.getQty()) <= inventory.getQuantity()) {
					if (productsEntity1.getPrice() > inventory.getPurchasePrice()) {
						double sellingPrice = productsEntity1.getPrice();
						double purchasePrice = inventory.getPurchasePrice();
						double profit = sellingPrice - purchasePrice;
						inventory.setProfit(inventory.getProfit() + profit);
						// total profit/loss
						inventory.setTotal(inventory.getProfit() + inventory.getLoss());

						// System.err.println(inventory.getTotal());
					} else if (productsEntity1.getPrice() < inventory.getPurchasePrice()) {
						double loss = productsEntity1.getPrice() - inventory.getPurchasePrice();
						inventory.setLoss(inventory.getLoss() - loss);
						inventory.setTotal(inventory.getProfit() - inventory.getLoss());
					}

					/////////
					inventory.setQuantity(inventory.getQuantity() - Long.valueOf(productsEntity1.getQty()));
					addinvenInventories.add(inventory);
					if (inventory.getQuantity() == 0) {
						ProductsEntity productsEntity = productsRepo.findByProdName(productsEntity1.getProdName());
						productsEntity.setStatus("Not Available");
						productsRepo.saveProduct(productsEntity);
					}
				} else {
					i++;
				}
				cart.setProductsEntity(productsEntity1);
				setOrderedItems(cart, ordersEntity);
			}
			if (i == 0) {
				inventoryRepo.saveAllInventories(addinvenInventories);
			} else {
				throw new RuntimeErrorException(null);
			}

			// save orders
			ordersEntity.setUserName(totalCartList.getUserName());
			LocalDate date = LocalDate.now();
			ordersEntity.setDeliveryDate(date.toString());
			List<AddressEntity> addressEntity = registrationRepo.findByAddressUserName(userName);
			System.out.println(addressEntity);
			Iterator<?> iterator = addressEntity.iterator();

			while (iterator.hasNext()) {
				AddressEntity addressEntity2 = (AddressEntity) iterator.next();
				if (addressEntity2.isDeafultAddress() == true)
					ordersEntity.setAddressEntity(addressEntity2);
				ordersEntity.setAddressId(addressEntity2.getId());
				ordersEntity.setPaymentMode("COD");
				ordersEntity.setPaymentStatus("Pending");
				ordersEntity.setAmount(String.valueOf(totalCartList.getTotalCost()));
				ordersRepo.save(ordersEntity);
			}
			
			ordersRepo.save(ordersEntity);

			return ordersEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("error Message", e.getLocalizedMessage());
		}
	}

	public List<OrderedItems> setOrderedItems(MyCart cart, OrdersEntity ordersEntity) {
		try {
			OrderedItems orderedItems = new OrderedItems();
			orderedItems.setItemCost(cart.getProductsEntity().getPrice());
			orderedItems.setItemName(cart.getProductsEntity().getProdName());
			orderedItems.setPrice(cart.getProductsEntity().getPrice() * cart.getQty());
			orderedItems.setQuantity(cart.getQty());
			orderedItems.setOrdersEntity(ordersEntity);
			orderedItems.setProductsEntity(cart.getProductsEntity());
			List<OrderedItems> orderedItemsList = new ArrayList<>();
			orderedItemsList.add(orderedItemsRepo.save(orderedItems));
			return orderedItemsList;
		} catch (Exception e) {
			throw new CustomException("error message", e.getLocalizedMessage());
		}
	}

	// to find the ordered items
	@Override
	public List<OrdersEntity> findMyOrders(String userName) {
		try {
			List<OrdersEntity> ordersEntity = ordersRepo.findByUserName(userName);
			return ordersEntity;
		} catch (NullPointerException e) {
			throw new CustomException("error occured", e.getLocalizedMessage());
		}
	}

	@Override
	public void export(HttpServletResponse response, String orderId) {

		try {

			OrdersEntity ordersEntityList = ordersRepo.findByOrderId(orderId);

			List<OrderedItems> orderedItemsList = orderedItemsRepo.findByOrdersEntity(ordersEntityList);
			RegistrationEntity registrationEntity = registrationRepo.findByUserName(ordersEntityList.getUserName());
			
			AddressEntity addressEntity = registrationRepo.findByAddressId(ordersEntityList.getAddressId());
			
			
			Document document = new Document(PageSize.A4);
			PdfWriter.getInstance(document, response.getOutputStream());

			Image omgLogo = Image.getInstance("omg.png");
			omgLogo.scaleAbsolute(120, 40);
			omgLogo.setAlignment(50);

			Image thanksLogo = Image.getInstance("thankslogo.png");
			thanksLogo.scaleAbsolute(400, 150);
			// thanksLogo.setAlignment(70);

			document.open();
			Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE);
			fontTitle.setSize(18);
			fontTitle.setColor(19, 124, 50);

			Header Invoiceheader = new Header("Invoice", "0");

			Paragraph orderIdLine = new Paragraph("Order #  : " + orderId + " omg.com");
			orderIdLine.setAlignment(Paragraph.ALIGN_CENTER);

			Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
			fontParagraph.setSize(12);
			// fontParagraph.setColor(206,33,33);

			Paragraph invoiceHeading = new Paragraph("Invoice", fontTitle);
			invoiceHeading.setSpacingAfter(2f);
			invoiceHeading.setAlignment(Paragraph.ALIGN_CENTER);

			Paragraph deliveryLocation = new Paragraph("Delivery Location  :",
					FontFactory.getFont(FontFactory.HELVETICA_BOLD));
			
			String deliveryAddressName = 
					registrationEntity.getFirstName() +" "+ registrationEntity.getLastName() + "\n"
					+ addressEntity.getHouseNo() + "," + addressEntity.getLandmark() + "\n" + addressEntity.getState()
					+ "," + addressEntity.getPincode();

			Paragraph deliveryAdd = new Paragraph(deliveryAddressName);

			Paragraph orderID = new Paragraph("Order ID", fontParagraph);

			Table orderTable = new Table(2, 6);
			orderTable.setAlignment(5);
			orderTable.setBorder(2);
			orderTable.setPadding(6);
			orderTable.addCell(orderID);
			orderTable.addCell(String.valueOf(ordersEntityList.getOrderId()));
			orderTable.addCell("Delivey Date ");
			orderTable.addCell(ordersEntityList.getDeliveryDate());
//			orderTable.addCell(new Paragraph("Final Total", fontParagraph));
//			orderTable.addCell(String.valueOf(ordersEntityList.getAmount()) + " rs");
			orderTable.addCell("Payment By");
			orderTable.addCell(ordersEntityList.getPaymentMode());
			orderTable.addCell("Amount payable");
			orderTable.addCell(String.valueOf(ordersEntityList.getAmount()) + " rs");

			Table itemListTable = new Table(5);
//			itemListTable.setAlignment(5);
			itemListTable.setPadding(6);

			itemListTable.addCell("Sl No ");
			itemListTable.addCell("Item");
			itemListTable.addCell("Unit Cost");
			itemListTable.addCell("quantity");
			itemListTable.addCell("Price");

			int numberOfItems = 1;
			for (OrderedItems oi : orderedItemsList) {

				Cell orderitemscell = new Cell(String.valueOf(numberOfItems));
				orderitemscell.setBorder(orderitemscell.NO_BORDER);
				itemListTable.addCell(orderitemscell);

				Cell itemname = new Cell(oi.getItemName());
				itemname.setBorder(orderitemscell.NO_BORDER);
				itemListTable.addCell(itemname);

				Cell itemcost = new Cell(String.valueOf(String.valueOf(oi.getItemCost())) + "0");
				itemcost.setBorder(itemcost.NO_BORDER);
				itemListTable.addCell(itemcost);

				Cell quantity = new Cell(String.valueOf(String.valueOf(oi.getQuantity())) + "0");
				quantity.setBorder(itemcost.NO_BORDER);
				itemListTable.addCell(quantity);

				Cell Price = new Cell(String.valueOf(String.valueOf(oi.getPrice())) + "0");
				Price.setBorder(itemcost.NO_BORDER);
				itemListTable.addCell(Price);

				numberOfItems++;
			}

			Cell emptyCell = new Cell("");
			emptyCell.disableBorderSide(emptyCell.RIGHT);
			emptyCell.enableBorderSide(emptyCell.TOP);

			if (numberOfItems > 1) {

				itemListTable.addCell(emptyCell);

				itemListTable.addCell(emptyCell);

				itemListTable.addCell(emptyCell);

				Cell subCell = new Cell("subtotal ");
				subCell.disableBorderSide(subCell.LEFT);
				subCell.enableBorderSide(subCell.TOP);
				itemListTable.addCell(subCell);
				itemListTable.addCell("0");

				///////////
				itemListTable.addCell(emptyCell);

				itemListTable.addCell(emptyCell);

				itemListTable.addCell(emptyCell);

				Cell DetailsCell = new Cell("Delivery Fee ");
				DetailsCell.setHorizontalAlignment("Center");
				DetailsCell.disableBorderSide(DetailsCell.LEFT);
				DetailsCell.enableBorderSide(DetailsCell.TOP);
				itemListTable.addCell(DetailsCell);
				itemListTable.addCell("0");

				///////////////////
				itemListTable.addCell(emptyCell);

				itemListTable.addCell(emptyCell);

				itemListTable.addCell(emptyCell);

				Cell finaltotalcell = new Cell("Final Total :");
				finaltotalcell.disableBorderSide(finaltotalcell.LEFT);
				finaltotalcell.enableBorderSide(finaltotalcell.TOP);
				itemListTable.addCell(finaltotalcell);
				itemListTable.addCell("0");

			}

			itemListTable.setWidth(100);

			// orderTable.setWidth(50);

			// orderTable.setAlignment(Table.ALIGN_LEFT);

			itemListTable.setOffset(40);

			Table table = new Table(2);
			table.setBorder(Table.NO_BORDER);
			Cell cell = new Cell();
			// cell.add();
			cell.setBorder(cell.NO_BORDER);
			cell.add(deliveryLocation);
			cell.setVerticalAlignment(Cell.ALIGN_BOTTOM);
			table.addCell(cell);
			Cell cell2 = new Cell(omgLogo);
			cell2.setBorder(cell2.NO_BORDER);
			table.addCell(cell2);

			Cell cell3 = new Cell();
			cell3.setBorder(cell3.NO_BORDER);
			cell3.add(deliveryAdd);
			table.addCell(cell3);
			Cell cell4 = new Cell(deliveryAdd);
			cell4.setBorder(cell4.NO_BORDER);
			table.addCell(cell4);

			Cell cell5 = new Cell();
			cell5.setBorder(cell5.NO_BORDER);
			cell5.add(orderTable);
			table.addCell(cell5);

			Cell cell6 = new Cell(thanksLogo);

			cell6.setBorder(cell6.NO_BORDER);

			cell6.setHorizontalAlignment(cell6.ALIGN_RIGHT);

			table.addCell(cell6);

			table.setPadding(4f);

			document.add(orderIdLine);
			document.add(invoiceHeading);
			document.add(table);
//			document.add(omgLogo);
//			document.add(deliveryLocation);
//			document.add(deliveryAddressName);
//			document.add(deliveryAdressHno);
//			document.add(deliveryAddressState);
//			document.add(orderTable);

			document.add(itemListTable);
			document.close();

		} catch (Exception e) {
			throw new CustomException("error occured", e.getLocalizedMessage());
		}
	}

	@Override
	public String cancelOrders(String orderId) {
		OrdersEntity ordersEntity=ordersRepo.findByOrderId(orderId);
		ordersEntity.setDeliveryStatus("CANCELLED");
		ordersRepo.save(ordersEntity);
		return "Your orders has been cancelled .";
	}


}