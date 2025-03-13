## ArrayList

- 数组可以存储基本数据类型和引用数据类型，集合只能存储引用数据类型，如果要存储基本数据类型，必须要打包成包装类次啊能存储。

### 成员方法

- 添加元素，返回值表示是否添加成功

```java
boolean add(E e)
```

- 删除指定元素,返回值表示是否删除成功

```java
boolean remove(E e)
```

- 删除指定索引的元素,返回被删除的元素

```java
E remove(int index)
```

- 修改指定索引下的元素,返回原来的元素

```java
E set(int index,E e)
```

- 获取指定索引的元素

```java
E get(int index)
```

- 返回集合的长度

```java
int size()
```



## LinkedList

- 底层数据结构是双向链表。这使LinkedList的查询慢、增删快。但如果查询的是首尾元素也很快

### LinkedList特有的API

（用到很少，主要还是collection和list中的方法）

- 在该列表开头插入指定元素

```java
public void addFirst(E e)
```



- 将指定的元素追加到此列表的末尾

```java
public void addLast(E e)
```



- 返回此列表中的第一个元素

```java
public E getFirst()
```



- 返回此列表中的最后一个元素

```java
public E getLast()
```



- 从此列表中删除并返回第一个元素

```java
public E removeFirst()
```



- 从此列表中删除并返回最后一个元素

```java
public E removeLast()
```

