### TableInfo

```java
public class TableInfo {
    //额外信息
    /**
     * 作者
     */
    private String author;
    /**
     * 分隔符
     */
    private String delimiter;
    /**
     * 自定义
     */
    private String customize;
    /**
     * 有日期
     */
    private boolean hasDate = false;
    /**
     * 可以为空
     */
    private boolean nullable = true;
    //额外结束
    /**
     * 原产地名称
     */
    private String originName;
    /**
     * 表前缀
     */
    private String tablePrefix = ConfigHolder.TABLE_CONFIG.getPrefix();
    /**
     * 名字 去掉前缀的名字
     */
    private String name;
    /**
     * 注释
     */
    private String comment;
    /**
     * 大驼峰
     */
    private String upperCamel;
    /**
     * 小驼峰
     */
    private String lowerCamel;
    /**
     * 下划线
     */
    private String underscore;
    /**
     * 导入列表
     */
    private Set<String> importSet = new HashSet<>();
    /**
     * 注解列表
     */
    private List<String> annotationList = new ArrayList<>();

    /**
     * 字段信息列表
     */
    private List<FieldInfo> fieldInfoList = new ArrayList<>();
}
```

### FieldInfo

```java
public class FieldInfo {
    /**
     * 名字
     */
    private String originName;
    /**
     * 大驼峰
     */
    private String upperCamel;
    /**
     * 小驼峰
     */
    private String lowerCamel;
    /**
     * 下划线
     */
    private String underscore;
    /**
     * 来源类型
     */
    private String originType;
    /**
     * 类型
     */
    private FieldType type;
    /**
     * 备注
     */
    private String comment;
    /**
     * 注解列表
     */
    private List<String> annotationList;

    /**
     * 0 (columnNoNulls) - 该列不允许为空
     * 1 (columnNullable) - 该列允许为空
     * 2 (columnNullableUnknown) - 不确定该列是否为空
     */
    private int nullable;
}
```

### FieldType

```java
public class FieldType {
    /**
     * 包裹
     */
    private String pkg;
    /**
     * 类型
     */
    private String type;
    /**
     * ts型
     */
    private String tsType;
}
```

###

```java
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
}
```
