package com.real.matcher;

import java.util.List;

public interface Matcher {
  List<IdMapping> match(DatabaseType databaseType, CsvStream externalDb);
}