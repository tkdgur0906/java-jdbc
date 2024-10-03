package com.interface21.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetExtractor<T> {

    T extractData(ResultSet rs) throws SQLException;
}
