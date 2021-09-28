package com.example.jcurrencyapp.data.provider;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.jcurrencyapp.HibernateUtil;
import com.example.jcurrencyapp.exceptions.DatabaseProviderException;
import com.example.jcurrencyapp.model.CurrencyTypes;
import com.example.jcurrencyapp.model.Rate;
import com.example.jcurrencyapp.model.db.Country;
import com.example.jcurrencyapp.model.db.Currency;
import com.example.jcurrencyapp.model.db.Quotation;

public class DatabaseProviderImpl implements Provider {

	public Currency readCurrency(CurrencyTypes code) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Query query = session.getNamedQuery("Currency.findByCode");
		query.setParameter("currencyCode", code.toString());

		return (Currency) query.getSingleResult();
	}

	public Quotation readQuotation(Currency currency, LocalDate date) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Query query = session.getNamedQuery("Quotation.findByCodeAndDate");
		query.setParameter("date", date);
		query.setParameter("currency", currency);

		return (Quotation) query.getSingleResult();
	}

	public List<Quotation> readQuotation(Currency currency, LocalDate startDate, LocalDate endDate) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Query query = session.getNamedQuery("Quotation.findByCodeAndDateRange");
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("currency", currency);

		return (List<Quotation>) query.getResultList();
	}

	@Override
	public BigDecimal getRate(CurrencyTypes code, LocalDate date) {
		try {
			return this.readQuotation(this.readCurrency(code), date).getRate();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void saveRate(Rate rate) {
		Currency currency;
		Quotation quotation;

		try {
			currency = this.readCurrency(rate.getCode());
		} catch (NoResultException e) {
			throw new DatabaseProviderException("Any currency in database for code=" + rate.getCode(), new Throwable());
		}

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			quotation = new Quotation(currency, rate.getDate(), rate.getRate());
			session.save(quotation);
			tx.commit();
			currency.addQuotation(quotation);
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	@Override
	public List<Rate> getRates(CurrencyTypes code, LocalDate startDate, LocalDate endDate) {
		List<Quotation> quotations;
		List<Rate> rates = new ArrayList<Rate>();

		try {
			quotations = this.readQuotation(this.readCurrency(code), startDate, endDate);
			for (Quotation quotation : quotations) {
				rates.add(
						new Rate(quotation.getCurrency().getCurrencyCode(), quotation.getDate(), quotation.getRate()));
			}

		} catch (NoResultException e) {
			return null;
		}

		return rates;
	}

	@Override
	public void saveRates(List<Rate> rates) {
		if (rates != null) {
			for (Rate rate : rates) {
				this.saveRate(rate);
			}
		}
	}

}
