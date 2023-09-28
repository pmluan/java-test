package hcmus.edu.vn.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hcmus.edu.vn.utils.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;


import hcmus.edu.vn.models.AcctItem;
import hcmus.edu.vn.models.TestModel;

public class Main {

	public static void main(String[] args) {

//		String cardNumber = "547451******1569";
//		cardNumber = StringUtils.join(StringUtils.substring(cardNumber, 0, 4) , StringUtils.SPACE, StringUtils.substring(cardNumber, 4, 8),
//				StringUtils.SPACE, StringUtils.substring(cardNumber, 8, 12) , StringUtils.SPACE , StringUtils.substring(cardNumber, 12, cardNumber.length()));

//		System.out.println(formatDate("14/12/2000", "dd/MM/yyyy", "ddMMyy"));

		String json = "[{\"acctno\":\"1\",\"custName\":\"2\"},{\"acctno\":\"1\",\"custName\":\"3\"},{\"acctno\":\"2\",\"custName\":\"2\"},{\"acctno\":\"3\",\"custName\":\"2\"},{\"acctno\":\"1\",\"custName\":\"5\"},{\"acctno\":\"1\",\"custName\":\"2\"}]";

		List<AcctItem> acctItems = Jackson.stringToList(json, AcctItem.class);

		List<TestModel> list = new ArrayList<>();

		for (AcctItem i : acctItems) {
			List<String> as = new ArrayList<>();
			as.add(i.getCustName());

			TestModel model = new TestModel();
			model.setA(i.getAcctno());
			model.setB(as);

			boolean match = list.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item.getA(), i.getAcctno()));
			if (match) {
				TestModel model2 = list.stream()
						.filter(item -> StringUtils.equalsIgnoreCase(item.getA(), i.getAcctno())).findAny()
						.orElse(null);
				if (model2 != null) {
					List<String> t = model2.getB();
					boolean c = t.stream().anyMatch(item -> StringUtils.equalsIgnoreCase(item, i.getCustName()));
					if (!c) {
						model2.getB().add(i.getCustName());
					}
				}
			} else {
				list.add(model);
			}

		}

		System.out.println(Jackson.toJsonString(list));
	}

	public static String formatDate(String dateStr, String currentPatter, String newPattern) {
		try {
			Date date = DateUtils.parseDate(dateStr, currentPatter);
			if (date != null) {
				return DateFormatUtils.format(date, newPattern);
			}
			return StringUtils.EMPTY;
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}

}
