package com.hilltoponline.repository;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hilltoponline.controller.TermController;
import com.hilltoponline.model.Term;

@Repository
public class TermRepository {
	private final static Logger LOG = LoggerFactory.getLogger(TermController.class);

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Term> getAllTerms() {
		String sql = "SELECT * FROM Terms";
    	return sortTermList(this.jdbcTemplate.query(sql, new Term.termMapper()));
	}
	public boolean addTerm(Term term){
		LOG.error(term.toString());
		String termSemester = term.getTermSemester();
		int termYear = term.getTermYear();
		boolean activeTerm = term.getTermActive();
		boolean   termOpenForRegistration = term.getTermOpenForRegistration();
		String sql = "INSERT INTO Terms(termSemester,termYear,termActive, termOpenForRegistration) VALUES (?,?,?,?)";
		Integer rows = this.jdbcTemplate.update(sql,termSemester,termYear,activeTerm,termOpenForRegistration);
		return rows > 0;
	}
	public boolean editTerm(Term term){
		String sql = "UPDATE Terms SET termSemester = ?, termYear = ?, termActive = ?, termOpenForRegistration= ? WHERE termId = ?";
		Integer rows = this.jdbcTemplate.update(sql,term.getTermSemester(),term.getTermYear(),term.getTermActive(),term.getTermOpenForRegistration(),term.getTermId());
		return rows > 0;
	}

	public boolean removeTerm(Term term){
		int termId = term.getTermId();
		String sql ="DELETE FROM Terms where termId = ?";
		Integer rows = this.jdbcTemplate.update(sql,termId);
		
		return rows > 0;
	}
	public Term getActiveTerm(){
		String sql = "SELECT * FROM Terms WHERE termActive = true";
		
		
		return this.jdbcTemplate.queryForObject(sql, new Term.termMapper());
	}
	public Term getTermById(int termId){
		String sql ="SELECT * FROM Terms WHERE termId = ?";
		
		return this.jdbcTemplate.queryForObject(sql, new Object[]{termId}, new Term.termMapper());

	}
	public List<Term> termActiveForRegistration(){
		String sql ="SELECT * FROM Terms WHERE termOpenForRegistration = ?";
		return this.jdbcTemplate.query(sql, new Object[]{true}, new Term.termMapper());
	}
	
	
	// WILL SORT TERMS IN DESCENDING ORDER.
	public List<Term> sortTermList(List<Term> terms) 
	{
		Term[] termArray = terms.toArray(new Term[terms.size()]);
		sortTerms(termArray);
		return Arrays.asList(termArray);
	}

	private static Term[] sortTerms(Term[] list) 
	{
		if (list.length <= 1) {
			return list;
		}
		Term[] first = new Term[list.length / 2];
		Term[] second = new Term[list.length - first.length];
		System.arraycopy(list, 0, first, 0, first.length);
		System.arraycopy(list, first.length, second, 0, second.length);
		sortTerms(first);
		sortTerms(second);
		merge(first, second, list);
		return list;
	}

	private static void merge(Term[] first, Term[] second, Term[] result) 
	{
		int iFirst = 0;
		int iSecond = 0;
		int iMerged = 0;
		Map<String, Integer> semesterRankMap = new LinkedHashMap<String,Integer>();
		semesterRankMap.put("Spring", 1);
		semesterRankMap.put("Summer", 2);
		semesterRankMap.put("Fall", 3);
		while (iFirst < first.length && iSecond < second.length) 
		{
			if (first[iFirst].getTermYear()==second[iSecond].getTermYear()) 
			{
				if(semesterRankMap.get(first[iFirst]) > semesterRankMap.get(second[iSecond].getTermSemester()))
				{
					result[iMerged] = first[iFirst];
					iFirst++;
				}
				else
				{
					result[iMerged] = second[iSecond];
					iSecond++;
				}
			} 
			else if (first[iFirst].getTermYear() > second[iSecond].getTermYear())
			{
				result[iMerged] = first[iFirst];
				iFirst++;
			}
			else
			{
				result[iMerged] = second[iSecond];
				iSecond++;
			}
			iMerged++;
		}
		System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);
		System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);
	}
	
	
}
