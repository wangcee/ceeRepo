Solr Query 语法
----------

Solr 查询无论是 Solrj 操作还是直接发送请求给 Solr 服务器，原理都是一样的，本质上都是发送 http 请求给 Solr 服务器，不过与 Solrj 操作 Solr 服务器不同，Solr 查询可以按照各种各样的形式返回结果，比如重要的 Json 格式，可以实现 Ajax 动态显示数据效果。

> Solr Wiki : https://wiki.apache.org/solr/SolrResources

> Solr 查询语法的特点(就是普通的 get 请求，key=value)

##### 常用的有

- q – 查询字符串，必须的。
- fl – 指定返回那些字段内容，用逗号或空格分隔多个。
- start – 返回第一条记录在完整找到结果中的偏移位置，0开始，一般分页用。
- rows – 指定返回结果最多有多少条记录，配合start来实现分页。
- sort – 排序，格式：sort=<field name>+<desc|asc>[,<field name>+<desc|asc>]… 。示例：（inStock desc, price asc）表示先 “inStock” 降序, 再 “price” 升序，默认是相关性降序。
- wt : (writer type)指定输出格式，可以有 xml, json, php, phps, 后面 solr 1.3增加的，要用通知我们，因为默认没有打开
- fq :（filter query）过虑查询，作用：在q查询符合结果中同时是fq查询符合的，例如：q=mm&fq=date_time:[20081001 TO 20091031]，找关键字mm，并且date_time是20081001到20091031之间的。[官方文档](http://wiki.apache.org/solr/CommonQueryParameters)


##### 不常用的有

- q.op – 覆盖schema.xml的defaultOperator（有空格时用”AND”还是用”OR”操作逻辑），一般默认指定
- df – 默认的查询字段，一般默认指定
- qt – （query type）指定那个类型来处理查询请求，一般不用指定，默认是standard。
- indent – 返回的结果是否缩进，默认关闭，用 indent=true|on 开启，一般调试json,php,phps,ruby输出才有必要用这个参数。
- version – 查询语法的版本，建议不使用它，由服务器指定默认值。

##### Solr的检索运算符

- “:” 指定字段查指定值，如返回所有值*:*²
- “?”²表示单个任意字符的通配
- “*” 表示多个任意字符的通配（不能在检索的项开始使用*或者?符号）²
- “~”²表示模糊检索，如检索拼写类似于”roam”的项这样写：roam~将找到形如foam和roams的单词；roam~0.8，检索返回相似度在0.8以上的记录。
- ²邻近检索，如检索相隔10个单词的”apache”和”jakarta”，”jakarta apache”~10
- “^”²控制相关度检索，如检索jakarta apache，同时希望去让”jakarta”的相关度更加好，那么在其后加上”^”符号和增量值，即jakarta^4 apache
- 布尔操作符AND、||²
- 布尔操作符OR、²&&
- 布尔操作符NOT、!、-²（排除操作符不能单独与项使用构成查询）
- “+” 存在操作符，要求符号”+”后的项必须在文档相应的域中存在²
- ( ) 用于构成子查询²
- ² [] 包含范围检索，如检索某时间段记录，包含头尾，date:[200707 TO 200710]
- {}²不包含范围检索，如检索某时间段记录，不包含头尾
- date:{200707 TO 200710}
- ” 转义操作符，特殊字符包括+ – & | ! ( ) { } [ ] ^ ” ~ * ? : “

##### Solr Stats 查询

[http://wiki.apache.org/solr/StatsComponent](http://wiki.apache.org/solr/StatsComponent "StatsComponent")

相当于MySQL中sum某些列的数字

- stats=true : 开启Stats查询
- stats.field : 指定要sum数据的字段，例如 stats.field=day1&stats.field=day2
- stats.facet : 
- rows=0 可以只返回stats合计数据

返回的数据格式： 

```Json
{
    "responseHeader": {
        "status": 0,
        "QTime": 33
    },
    "response": {
        "numFound": 1234567,   //一共多少条记录
        "start": 0,
        "maxScore": 1.1234567,
        "docs": []
    },
    "stats": {
        "stats_fields": {
            "day1": {
                "min": 0,		   //最小值
                "max": 555555,	   //最大值
                "count": 1234567,
                "missing": 0,
                "sum": 999999999,  //这是合计值
                "sumOfSquares": 3999999999999,
                "mean": 222.22222222222222,
                "stddev": 3333.3333333333,
                "facets": {}
            },
            "day2": {
                "min": 0,
                "max": 666666,
                "count": 1234567,
                "missing": 0,
                "sum": 888888888,   //这是合计值
                "sumOfSquares": 3333333333333,
                "mean": 228.888888888888888,
                "stddev": 4444.44444444444,
                "facets": {}
            }
        }
    }
}
```