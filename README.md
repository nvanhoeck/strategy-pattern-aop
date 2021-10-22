# strategy-pattern-aop

We have a situation where we want to apply the strategy pattern based on method level. So we want an interface or abstract class which has methods, and based on your security role you are allowed to execute a different implementation.

We use Spring AOP annotations, to execute the functionality to determine which class/functionality should be used:

So what do we want: to replace StrategyPattern class with an Interface or Abstract Class. Now we use a default class which does nothing, which is ugly.

So any suggestions how we do this, because the annotations only work with methods which should be executed.

The tests are successful, what we want is that StrategyPattern class becomes an interface and still make this work.
