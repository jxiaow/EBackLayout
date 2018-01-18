## EBackLayout

#### EBackLayout是基于[SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout) ，改动内容如下：

1. 升级gradle为3.0
2. 删除[SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout) 中app中除SwipeBackActivityHelper中的所有类
3. 利用google的组件 `Lifecycle`,修改SwipeBackActivityHelper，重命名为`SwipeBackHelper`,实现解耦操作，以后使用时不在需要继承

## 使用

**目前只能用于Activity!!!!**

**目前只能用于Activity!!!!**

**目前只能用于Activity!!!!**

#### Gradle:

	implementation 'cn.xwj:backlayout:0.0.1'

#### 在activity的`onCreate()`方法中，添加代码：

	SwipeBackHelper.create(this);