Haochu Chen
300067058

I wrote a simple integer calculator


First, a failing test:
![first_fail](img/first_fail.png)

Then, I made it pass:
![first_pass](img/first_pass.png)

After that, I add more test cases (minus, times, and divide) and failed:
![more_test_fail](img/more_test_fail.png)

Then made them pass:
![m_t_d_pass](img/m_t_d_pass.png)

I added a divide by 0 test case and failed:
![divide0_fail](img/divide0_fail.png)

Then made it pass:
![divide0_pass](img/divide0_pass.png)

This is the codes now:
![code](img/code.png)

Then I did some refactoring, added a calculate method and put all calculations into it:
![code_ref](img/code_ref.png)

And the tests still pass:
![still_pass](img/still_pass.png)


===============================================================================

Here are the screenshots that I compiled and ran tests of the elixir tic and fizzbuzz:
![tic](img/tic.png)
![fizzbuzz](img/fizzbuzz.png)

