package org.sampratistaana.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.formula.functions.T;
import org.sampratistaana.Messages;
import org.sampratistaana.beans.BankAccount;
import org.sampratistaana.beans.Ledger.TransactionMode;
import static org.sampratistaana.beans.Ledger.TransactionMode.*;
import org.sampratistaana.beans.Property;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class BookSaleControllerV2 extends BaseController {
	private static final String ROW_KEY="org.sampratistaana.controllers.BookSaleControllerV2.Row";
	private BooleanProperty shopSale = new SimpleBooleanProperty(false);
	private BooleanProperty memberSale = new SimpleBooleanProperty(false);
	private BooleanProperty individualSale = new SimpleBooleanProperty(false);

	@FXML private GridPane gridForm;

	@FXML private Label receiptNo;
	@FXML private ComboBox<Property> customerType;
	//Shop
	@FXML private Label shopNameLbl;
	@FXML private ComboBox<Property> shopNameLov;
	//Member
	@FXML private Label memberLbl;
	@FXML private Label memberNo;
	//Individual
	@FXML private Label individualLbl;
	@FXML private TextField individualTxt;
	@FXML private Label phoneLbl;
	@FXML private TextField phoneTxt;
	@FXML private Label emailLbl;
	@FXML private TextField emailTxt;
	@FXML private Label panLbl;
	@FXML private TextField panTxt;
	@FXML private Label addressLbl;
	@FXML private TextField addressTxt;

	@FXML private ComboBox<PaymentType> paymentType;
	@FXML private Label depositAccountLabel;
	@FXML private ComboBox<BankAccount> depositAccount;
	@FXML private TextField externalTranNo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setComboxItems(customerType,lov().getBookSaleTypes());
		customerType.valueProperty().addListener((obs,oldVal,newVal)-> {			
			shopSale.set("SHOP".equals(newVal.getPropertyValue()));
			memberSale.set("MEMBER".equals(newVal.getPropertyValue()));
			individualSale.set("OTHERS".equals(newVal.getPropertyValue()));
			reArrangeElements();
		});
		setComboxItems(shopNameLov, lov().getShopNames());

		bindVisibilityProp(shopSale,shopNameLbl,shopNameLov);
		customerType.setValue(customerType.getItems().get(0));
		shopNameLov.setValue(shopNameLov.getItems().get(0));

		bindVisibilityProp(memberSale, memberLbl,memberNo);
		bindVisibilityProp(individualSale, individualLbl,individualTxt
				,phoneLbl,phoneTxt,emailLbl,emailTxt,panLbl,panTxt,addressLbl,addressTxt);

		List<PaymentType> paymentTypeList=new ArrayList<>(4);
		paymentTypeList.add(new PaymentType("member.cash", CASH));
		paymentTypeList.add(new PaymentType("member.check", CHEQUE));
		paymentTypeList.add(new PaymentType("member.online", ONLINE));
		paymentTypeList.add(new PaymentType("member.dd", DD));
		setComboxItems(paymentType, paymentTypeList);

		paymentType.valueProperty().addListener((obs,oldVal,newVal)-> {
			depositAccount.setVisible(newVal.mode!=CASH);
			depositAccountLabel.setVisible(depositAccount.isVisible());
			externalTranNo.setDisable(!depositAccount.isVisible());
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void reArrangeElements() {
		for(Node node:gridForm.getChildren()) {
			Map prop=node.getProperties();
			if(!prop.containsKey(ROW_KEY)) {
				Object val=GridPane.getRowIndex(node);
				prop.put(ROW_KEY, val==null?0:val);
			}
			int row=(Integer)prop.get(ROW_KEY);
			if(row>0) {
				GridPane.setRowIndex(node, individualSale.get()?row:row-1);
			}
		}
	}

	private void bindVisibilityProp(BooleanProperty boolProp,Node... nodes) {
		for(Node node:nodes) {
			node.visibleProperty().bindBidirectional(boolProp);
		}
	}

	public static class PaymentType{
		String key;
		TransactionMode mode;
		PaymentType(String key,TransactionMode mode) {
			this.mode=mode;
			this.key=key;
		}
		@Override
		public String toString() {
			return Messages.getMessage(key);
		}

	}
}
