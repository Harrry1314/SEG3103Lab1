Haochu Chen
300067058

Application:
![calculator](img/calculator.png)

Code changed is in /calculator

Analysis:  
&NewLine;
&NewLine;
&NewLine;
&NewLine;
&NewLine;
</br>
</br>
</br>
</br>
</br>
![equals](img/equals.png)
“==” cannot be used to compare two strings, “==” is used to compare the memory address of two strings, “equals()” should be used here.
  
  
![swing](img/swing.png)
“setVisible()” will cause the events dispatching on components in muti-threads of JFrame. These threads are asynchronized, so it may cause some problems, like deadlock. So I put it into a swing thread.

![innerclass](img/innerclass.png)
This inner class is anonymous and not static, so it cannot be accessed outside and static methods. So I declared it as a static named class.

![notused](img/notused.png)
This method is never been used, so I commented it.

![double](img/double.png)
It takes two steps to get a double type, which is not efficient, so I used “Double.parseDouble()” instead.

![double2](img/double2.png)
Same as above.

![final](img/final.png)
These final fields are initialized as they were declared. It is better to make them static final.

![ifelse](img/ifelse.png)
If a variable is not <= 2, than it must be >=3, so “i>=3” is useless, so I removed it, same as the “i>=8” and “i>=13”.

![uselessif](img/uselessif.png)
These if else statements covered all situations of I from 0 to 18, which is all situations I can be in the loop, and they do the same things for all if else statements. So I commented them all and coded “getContentPane().add(buttons[i]);” only once.

![default](img/default.png)
This switch case statement has no default block, I added it.

![morenums](img/morenums.png)
If “!morenums” is true, than there is no need to judge the “morenums” again. So I used a single “else” instead of “else if (morenums)”.
