package com.cts.migration.entity;

public interface IQuery {

	public static final String SELECT = "SELECT ";
	public static final String UPDATE = "UPDATE ";
	public static final String INSERT = "INSERT ";
	public static final String DELETE = "DELETE ";
	public static final String ALL = "* ";
	public static final String FROM = "FROM ";
	public static final String WHERE = "WHERE ";
	public static final String AND = "AND ";
	public static final String OR = "OR ";
	public static final String GREATER_THAN = "> ";
	public static final String LESS_THAN = "< ";
	public static final String EQUALS = "= ";
	public static final String NOT_EQUALS = "<> ";
	public static final String PARENTHESIS_OPEN = "( ";
	public static final String PARENTHESIS_CLOSE = ") ";
	public static final String INTO = "INTO ";
	public static final String COMMA = ", ";
	public static final String SET = "SET ";
	public static final String MODIFY = "MODIFY ";
	public static final String VALUES = "VALUES ";
	public static final String PARAM = "? ";
	public static final String SPACE = " ";


	public abstract String getInsertQuery();

	public abstract String getUpdateByIdQuery();

	public abstract String getDeletByIdQuery();
}
