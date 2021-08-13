/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.pkh.database;

import lombok.Getter;

/**
 * 表字段类型
 *
 * @author hubin
 * @since 2017-01-11
 */
@Getter
public enum EFieldType {
    // 基本类型
    BASE_BYTE("byte", null, "number"),
    BASE_SHORT("short", null, "number"),
    BASE_CHAR("char", null, "string"),
    BASE_INT("int", null, "number"),
    BASE_LONG("long", null, "number"),
    BASE_FLOAT("float", null, "number"),
    BASE_DOUBLE("double", null, "number"),
    BASE_BOOLEAN("boolean", null, "boolean"),

    // 包装类型
    BYTE("Byte", null, "number"),
    SHORT("Short", null, "number"),
    CHARACTER("Character", null, "string"),
    INTEGER("Integer", null, "number"),
    LONG("Long", null, "number"),
    FLOAT("Float", null, "number"),
    DOUBLE("Double", null, "number"),
    BOOLEAN("Boolean", null, "boolean"),
    STRING("String", null, "string"),

    // sql 包下数据类型
    DATE_SQL("Date", "java.sql.Date", "Date"),
    TIME("Time", "java.sql.Time", "Date"),
    TIMESTAMP("Timestamp", "java.sql.Timestamp", "Date"),
    BLOB("Blob", "java.sql.Blob", "string"),
    CLOB("Clob", "java.sql.Clob", "string"),

    // java8 新时间类型
    LOCAL_DATE("LocalDate", "java.time.LocalDate", "Date"),
    LOCAL_TIME("LocalTime", "java.time.LocalTime", "Date"),
    YEAR("Year", "java.time.Year", "Date"),
    YEAR_MONTH("YearMonth", "java.time.YearMonth", "Date"),
    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime", "Date"),
    INSTANT("Instant", "java.time.Instant", "any"),

    // 其他杂类
    BYTE_ARRAY("byte[]", null, "any"),
    OBJECT("Object", null, "any"),
    DATE("Date", "java.util.Date", "Date"),
    BIG_INTEGER("BigInteger", "java.math.BigInteger", "number"),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal", "number");

    /**
     * 类型
     */
    private final FieldType type;

    EFieldType(String type, String pkg, String tsType) {
        this.type = new FieldType(pkg, type, tsType);
    }
}
