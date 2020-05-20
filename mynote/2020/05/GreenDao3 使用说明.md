GreenDao 3
一个将对象映射到 SQLite 数据库中的轻量且快速的ORM解决方案

资料
Github
官网

android-database-sqlcipher Github
数据库加密
GreenDaoUpgradeHelper Github
数据库升级辅助

配置
导入
配置项目的 build.gradle
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'
    }
}

// 使用数据库升级辅助GreenDaoUpgradeHelper时添加
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
配置模组的 build.gradle
apply plugin: 'org.greenrobot.greendao'

dependencies {
    compile 'org.greenrobot:greendao:3.2.2'

    // 使用数据库加密时添加
    compile 'net.zetetic:android-database-sqlcipher:3.5.6'

    // 使用数据库升级辅助GreenDaoUpgradeHelper时添加
    compile 'com.github.yuweiguocn:GreenDaoUpgradeHelper:v2.0.1'
}
参数
设置 Schema，在模组的 build.gradle 中添加：
schemaVersion：数据库schema版本号，通过*OpenHelpers迁移数据，schema改变值增加。默认为1
daoPackage：生成DAOs、DaoMaster、DaoSession的包名。默认为entities所在包名。
targetGenDir：生成DAOs、DaoMaster、DaoSession的目录。默认为build/generated/source/greendao
generateTests: 设置true自动生成单元测试。
targetGenDirTests: 设置生成单元测试目录。默认为src/androidTest/java
greendao {
    schemaVersion 1
    daoPackage 'com.example.greendaodemo.dao'
    targetGenDir 'src/main/java'
}
混淆
配置模组的 proguard-rules.pro
### greenDAO 3
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use RxJava:
-dontwarn rx.**
基本用法
实体
@Entity(
        // schema 名，多个 schema 时设置关联实体。插件产生不支持，需使用产生器
        // schema = "myschema",

        // 标记一个实体是否处于活动状态，活动实体有 update、delete、refresh 方法。默认为 false
        active = false,

        // 表名，默认为类名
        nameInDb = "AWESOME_USERS",

        // 定义多列索引
        indexes = {
                @Index(value = "name DESC", unique = true)
        },

        // 标记是否创建表，默认 true。多实体对应一个表或者表已创建，不需要 greenDAO 创建时设置 false
        createInDb = true,

        // 是否产生所有参数构造器。默认为 true。无参构造器必定产生
        generateConstructors = true,

        // 如果没有 get/set 方法，是否生成。默认为 true
        generateGettersSetters = true
)
public class User {
    // 数据库主键，autoincrement设置自增，只能为 long/ Long 类型
    @Id(autoincrement = true)
    private Long id;

    // 唯一，默认索引。可另定义属性唯一索引设为主键
    @Unique
    private String userId;

    // 列名，默认使用变量名。默认变化：userName --> USER_NAME
    @Property(nameInDb = "USERNAME")
    private String userName;

    // 索引，unique设置唯一，name设置索引别名
    @Index(unique = true)
    private long fk_dogId;

    // 非空
    @NotNull
    private String horseName;

    // 忽略，不持久化，可用关键字transient替代
    @Transient
    private int tempUsageCount;

    // 对一，实体属性 joinProperty 对应外联实体ID
    @ToOne(joinProperty = "fk_dogId")
    private Dog dog;

    // 对多。实体ID对应外联实体属性 referencedJoinProperty
    @ToMany(referencedJoinProperty = "fk_userId")
    private List<Cat> cats;

    // 对多。@JoinProperty：name 实体属性对应外联实体属性 referencedName
    @ToMany(joinProperties = {
            @JoinProperty(name = "horseName", referencedName = "name")
    })
    private List<Horse> horses;

    // 对多。@JoinEntity：entity 中间表；中间表属性 sourceProperty 对应实体ID；中间表属性 targetProperty 对应外联实体ID
    @ToMany
    @JoinEntity(
            entity = JoinUserWithSheep.class,
            sourceProperty = "uId",
            targetProperty = "sId"
    )
    private List<Sheep> sheep;
}
@Generated：greenDao生产代码注解，手动修改报错
@Keep：替换@Generated，greenDao不再生成，也不报错。@Generated(无hash)也有相同的效果
@ToOne：joinProperty 和对象联动，同时改变。对象懒加载，第一次请求后缓存
@ToMany：集合懒加载并缓存，之后获取集合不查找数据库，即集合数据不变。须手动修改集合，或调用reset方法清理集合
关系
User 和 Cat 配合产生一对多关系
@Entity
public class Cat {
    @Id
    private Long id;

    private String name;

    private long fk_userId;

    @ToOne(joinProperty = "fk_userId")
    private User user;
}
User、Sheep 和中间类 JoinUserWithSheep 配合产生多对多关系
@Entity
public class Sheep {
    @Id
    private Long id;

    private String name;

    @ToMany
    @JoinEntity(
            entity = JoinUserWithSheep.class,
            sourceProperty = "sId",
            targetProperty = "uId"
    )
    private List<User> users;
}

@Entity
public class JoinUserWithSheep {
    @Id
    private Long id;

    private Long uId;

    private Long sId;
}
多个 TreeNode 配合产生树状关系
@Entity
public class TreeNode {
    @Id
    private Long id;

    private Long parentId;

    @ToOne(joinProperty = "parentId")
    private TreeNode parent;

    @ToMany(referencedJoinProperty = "parentId")
    private List<TreeNode> children;
}
初始化
// Application 中执行
// DevOpenHelper 每次数据库升级会清空数据，一般用于开发
DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
Database db = helper.getWritableDb();
DaoSession daoSession = new DaoMaster(db).newSession();

// 在使用的地方获取 DAO
NoteDao noteDao = daoSession.getNoteDao();
查询日志
QueryBuilder.LOG_SQL = true;
QueryBuilder.LOG_VALUES = true;
DaoSession 增删改查
// DaoSession 的方法转换成 Dao 的对应方法执行
<T> long    insert(T entity)
<T> long    insertOrReplace(T entity)
<T> void    delete(T entity)
<T> void    deleteAll(java.lang.Class<T> entityClass)
<T> void    update(T entity)
<T,K> T     load(java.lang.Class<T> entityClass, K key)
<T,K> java.util.List<T>     loadAll(java.lang.Class<T> entityClass)
<T> QueryBuilder<T>     queryBuilder(java.lang.Class<T> entityClass)
<T,K> java.util.List<T>     queryRaw(java.lang.Class<T> entityClass, java.lang.String where, java.lang.String... selectionArgs)
<T> void    refresh(T entity)
void clear()  // 清理缓存域
Database getDatabase()
AbstractDao<?, ?> getDao(Class<? extends Object> entityClass)
Collection<AbstractDao<?, ?>> getAllDaos()
void runInTx(Runnable runnable)
<V> V callInTx(Callable<V> callable)
<V> V callInTxNoException(Callable<V> callable)
Dao 增加
long    insert(T entity)  // 插入指定实体
void    insertInTx(T... entities)
void    insertInTx(java.lang.Iterable<T> entities)
void    insertInTx(java.lang.Iterable<T> entities, boolean setPrimaryKey)
long    insertWithoutSettingPk(T entity)  // 插入指定实体，无主键
long    insertOrReplace(T entity)  // 插入或替换指定实体
void    insertOrReplaceInTx(T... entities)
void    insertOrReplaceInTx(java.lang.Iterable<T> entities)
void    insertOrReplaceInTx(java.lang.Iterable<T> entities, boolean setPrimaryKey)
void    save(T entity)  // 依赖指定的主键插入或修改实体
void    saveInTx(T... entities)
void    saveInTx(java.lang.Iterable<T> entities)
Dao 删除
void    deleteAll()  // 删除所有
void    delete(T entity)  // 删除指定的实体
void    deleteInTx(T... entities)
void    deleteInTx(java.lang.Iterable<T> entities)
void    deleteByKey(K key)  // 删除指定主键对应的实体
void    deleteByKeyInTx(K... keys)
void    deleteByKeyInTx(java.lang.Iterable<K> keys)
Dao 修改
void    update(T entity)
void    updateInTx(T... entities)
void    updateInTx(java.lang.Iterable<T> entities)
Dao 其它
void    refresh(T entity)  // 从数据库获取值刷新本地实体
long    count()  // 数量

boolean     detach(T entity)  // 从域中分离实体
void    detachAll()  // 从域中分离所有实体

AbstractDaoSession  getSession()
Database    getDatabase()
java.lang.String    getTablename()
java.lang.String[]  getAllColumns()
java.lang.String[]  getPkColumns()
java.lang.String[]  getNonPkColumns()
Property    getPkProperty()
Property[]  getProperties()
Dao 查询
java.util.List<T>   loadAll()
T   load(K key)
T   loadByRowId(long rowId)
QueryBuilder 查询
List joes = userDao.queryBuilder()  // 查询 User
                .where(Properties.FirstName.eq("Joe"))  // 首名为 Joe
                .orderAsc(Properties.LastName)  // 末名升序排列
                .list();  // 返回集合

// Joe，>= 1970.10
QueryBuilder qb = userDao.queryBuilder();
qb.where(Properties.FirstName.eq("Joe"),
                qb.or(Properties.YearOfBirth.gt(1970),
                                qb.and(Properties.YearOfBirth.eq(1970), Properties.MonthOfBirth.ge(10))));
List youngJoes = qb.list();
QueryBuilder<T>     queryBuilder()  // Dao

// QueryBuilder
QueryBuilder<T>     where(WhereCondition cond, WhereCondition... condMore)  // 条件，AND 连接
QueryBuilder<T>     whereOr(WhereCondition cond1, WhereCondition cond2, WhereCondition... condMore)  // 条件，OR 连接
QueryBuilder<T>     distinct()  // 去重，例如使用联合查询时
QueryBuilder<T>     limit(int limit)  // 限制返回数
QueryBuilder<T>     offset(int offset)  // 偏移结果起始位，配合limit(int)使用
QueryBuilder<T>     orderAsc(Property... properties)  // 排序，升序
QueryBuilder<T>     orderDesc(Property... properties)  // 排序，降序
QueryBuilder<T>     orderCustom(Property property, java.lang.String customOrderForProperty)  // 排序，自定义
QueryBuilder<T>     orderRaw(java.lang.String rawOrder)  // 排序，SQL 语句
QueryBuilder<T>     preferLocalizedStringOrder()  // 本地化字符串排序，用于加密数据库无效
QueryBuilder<T>     stringOrderCollation(java.lang.String stringOrderCollation)  // 自定义字符串排序，默认不区分大小写

WhereCondition  and(WhereCondition cond1, WhereCondition cond2, WhereCondition... condMore)  // 条件，AND 连接
WhereCondition  or(WhereCondition cond1, WhereCondition cond2, WhereCondition... condMore)  // 条件，OR 连接
Query 重复查询
// Joe，1970
Query query = userDao.queryBuilder().where(
    Properties.FirstName.eq("Joe"), Properties.YearOfBirth.eq(1970)
).build();
List joesOf1970 = query.list();

// Maria，1977
query.setParameter(0, "Maria");
query.setParameter(1, 1977);
List mariasOf1977 = query.list();
// QueryBuilder
Query<T>    build()
CursorQuery     buildCursor()
CountQuery<T>   buildCount()
DeleteQuery<T>  buildDelete()

// Query
// 设置查询参数，从 0 开始
Query<T>    setParameter(int index, java.lang.Object parameter)
Query<T>    setParameter(int index, java.lang.Boolean parameter)
Query<T>    setParameter(int index, java.util.Date parameter)
void    setLimit(int limit)  // 限制返回数
void    setOffset(int offset)  // 偏移结果起始位，配合limit(int)使用

// Query 绑定线程，执行非本线程的 Query 抛异常，调用获取本线程 Query
Query<T>    forCurrentThread()  // 获取本线程 Query
获取查询结果
// QueryBuilder、Query
T   unique()  // 返回唯一结果或者 null
T   uniqueOrThrow()  // 返回唯一非空结果，如果 null 则抛异常
java.util.List<T>   list()  // 返回结果集进内存
// 懒加载，须在 try/finally 代码中关闭。
LazyList<T>     listLazy()  // 第一次使用返回结果集，所有数据使用后会自动关闭
LazyList<T>     listLazyUncached()  // 返回虚拟结果集，数据库读取不缓存
CloseableListIterator<T>    listIterator()  // 懒加载数据迭代器，不缓存，所有数据使用后会自动关闭

// QueryBuilder、CountQuery
long    count()  // 获取结果数量
SQL 查询
// QueryBuilder.where() 配合 WhereCondition.StringCondition() 实现SQL查询
Query query = userDao.queryBuilder()
                .where(new WhereCondition.StringCondition("_ID IN (SELECT USER_ID FROM USER_MESSAGE WHERE READ_FLAG = 0)"))
                .build();

// Dao.queryRawCreate() 实现SQL查询
Query query = userDao.queryRawCreate(  ", GROUP G WHERE G.NAME=? AND T.GROUP_ID=G._ID", "admin");
// Dao
java.util.List<T>   queryRaw(java.lang.String where, java.lang.String... selectionArg)
Query<T>    queryRawCreate(java.lang.String where, java.lang.Object... selectionArg)
Query<T>    queryRawCreateListArgs(java.lang.String where, java.util.Collection<java.lang.Object> selectionArg)

// WhereCondition.PropertyCondition
PropertyCondition(Property property, java.lang.String op)
PropertyCondition(Property property, java.lang.String op, java.lang.Object value)
PropertyCondition(Property property, java.lang.String op, java.lang.Object[] values)

// WhereCondition.StringCondition
StringCondition(java.lang.String string)
StringCondition(java.lang.String string, java.lang.Object value)
StringCondition(java.lang.String string, java.lang.Object... values)
DeleteQuery 删除查询
DeleteQuery<T>  buildDelete()  // QueryBuilder
进阶用法
联合查询
// 芝麻街住户
QueryBuilder<User> queryBuilder = userDao.queryBuilder();
queryBuilder.join(Address.class, AddressDao.Properties.userId)
                .where(AddressDao.Properties.Street.eq("Sesame Street"));
List<User> users = queryBuilder.list();

// 欧洲超过百万人口的城市
QueryBuilder qb = cityDao.queryBuilder().where(Properties.Population.ge(1000000));
Join country = qb.join(Properties.CountryId, Country.class);
Join continent = qb.join(country, CountryDao.Properties.ContinentId,
                Continent.class, ContinentDao.Properties.Id);
continent.where(ContinentDao.Properties.Name.eq("Europe"));
List<City> bigEuropeanCities = qb.list();

// 爷爷叫林肯的人
QueryBuilder qb = personDao.queryBuilder();
Join father = qb.join(Person.class, Properties.FatherId);
Join grandfather = qb.join(father, Properties.FatherId, Person.class, Properties.Id);
grandfather.where(Properties.Name.eq("Lincoln"));
List<Person> lincolnDescendants = qb.list();
// QueryBuilder，联合查询
<J> Join<T,J>   join(java.lang.Class<J> destinationEntityClass, Property destinationProperty)
<J> Join<T,J>   join(Property sourceProperty, java.lang.Class<J> destinationEntityClass)
<J> Join<T,J>   join(Property sourceProperty, java.lang.Class<J> destinationEntityClass, Property destinationProperty)
<J> Join<T,J>   join(Join<?,T> sourceJoin, Property sourceProperty, java.lang.Class<J> destinationEntityClass, Property destinationProperty)
自定义类型
默认支持类型：byte[]、String、Date、
boolean、int、short、long、float、double、byte、
Boolean、Integer、Short、Long、Float、Double、Byte

// enum 转换为 Integer
@Entity
public class User {
    @Id
    private Long id;

    @Convert(converter = RoleConverter.class, columnType = Integer.class)
    private Role role;

    public enum Role {
        DEFAULT(0), AUTHOR(1), ADMIN(2);

        final int id; // 使用稳定的 id 来转换，不要使用不稳定的名字和顺序

        Role(int id) {
            this.id = id;
        }
    }

    public static class RoleConverter implements PropertyConverter<Role, Integer> {
        @Override
        public Role convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (Role role : Role.values()) {
                if (role.id == databaseValue) {
                    return role;
                }
            }
            return Role.DEFAULT; // 准备一个默认值，防止数据移除时崩溃
        }

        @Override
        public Integer convertToDatabaseValue(Role entityProperty) {
            // 判断返回 null
            return entityProperty == null ? null : entityProperty.id;
        }
    }
}
升级
使用 DevOpenHelper 每次升级数据库，表会删除重建，推荐开发使用。实际使用中建立类继承 DaoMaster.OpenHelper，实现 onUpgrade()

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        if (oldVersion == newVersion) {
            Log.d("onUpgrade", "数据库是最新版本" + oldVersion + "，不需要升级");
            return;
        }
        Log.d("onUpgrade", "数据库从版本" + oldVersion + "升级到版本" + newVersion);
        switch (oldVersion) {
            case 1:
                String sql = "";
                db.execSQL(sql);
            case 2:
            default:
                break;
        }
    }
}

// 初始化使用 MySQLiteOpenHelper
MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "notes-db", null);
Database db = helper.getWritableDb();
DaoSession daoSession = new DaoMaster(db).newSession();
另有升级辅助库 GreenDaoUpgradeHelper，通过 MigrationHelper 在删表重建的过程中，使用临时表保存数据并还原。

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, TestDataDao.class, TestData2Dao.class, TestData3Dao.class);
    }
}

// 初始化
MigrationHelper.DEBUG = true; //如果你想查看日志信息，请将 DEBUG 设置为 true
MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "test.db", null);
DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
高阶用法
SQLCipher 加密
使用getEncryptedReadableDb()和getEncryptedWritableDb()获取加密的数据库
256位AES加密，会提升APK的大小
Robolectric 测试时，须使用非加密数据库
public static final boolean ENCRYPTED = true;

MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, ENCRYPTED ? "notes-encrypted.db" : "notes.db", null);
Database db = ENCRYPTED ? helper.getEncryptedWritableDb("<your-secret-password>") : helper.getWritableDb();
daoSession = new DaoMaster(db).newSession();
RxJava 支持

// DaoSession
RxTransaction   rxTx()
RxTransaction   rxTxPlain()

// Dao
RxDao<T,K>  rx()
RxDao<T,K>  rxPlain()

// QueryBuilder
RxQuery<T>  rx()
RxQuery<T>  rxPlain()

作者：雨林雨林
链接：https://www.jianshu.com/p/4e6d72e7f57a
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。