Haochu Chen
<br/>
300067058

Application:
<br/>
![calculator](img/calculator.png)
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
Code changed is in /calculator
<br/>
<br/>
Analysis:
<br/>
![equals](img/equals.png)
<br/>
“==” cannot be used to compare two strings, “==” is used to compare the memory address of two strings, “equals()” should be used here.
<br/>
<br/>
<br/>
<br/>
![swing](img/swing.png)
<br/>
“setVisible()” will cause the events dispatching on components in muti-threads of JFrame. These threads are asynchronized, so it may cause some problems, like deadlock. So I put it into a swing thread.
<br/>
<br/>
<br/>
<br/>
![innerclass](img/innerclass.png)
<br/>
This inner class is anonymous and not static, so it cannot be accessed outside and static methods. So I declared it as a static named class.
<br/>
<br/>
<br/>
<br/>
![notused](img/notused.png)
<br/>
This method is never been used, so I commented it.
<br/>
<br/>
<br/>
<br/>
![double](img/double.png)
<br/>
It takes two steps to get a double type, which is not efficient, so I used “Double.parseDouble()” instead.
<br/>
<br/>
<br/>
<br/>
![double2](img/double2.png)
<br/>
Same as above.
<br/>
<br/>
<br/>
<br/>
![final](img/final.png)
<br/>
These final fields are initialized as they were declared. It is better to make them static final.
<br/>
<br/>
<br/>
<br/>
![ifelse](img/ifelse.png)
<br/>
If a variable is not <= 2, than it must be >=3, so “i>=3” is useless, so I removed it, same as the “i>=8” and “i>=13”.
<br/>
<br/>
<br/>
<br/>
![uselessif](img/uselessif.png)
<br/>
These if else statements covered all situations of I from 0 to 18, which is all situations I can be in the loop, and they do the same things for all if else statements. So I commented them all and coded “getContentPane().add(buttons[i]);” only once.
<br/>
<br/>
<br/>
<br/>
![default](img/default.png)
<br/>
This switch case statement has no default block, I added it.
<br/>
<br/>
<br/>
<br/>
![morenums](img/morenums.png)
<br/>
If “!morenums” is true, than there is no need to judge the “morenums” again. So I used a single “else” instead of “else if (morenums)”.
