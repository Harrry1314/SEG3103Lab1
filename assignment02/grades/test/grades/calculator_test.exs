defmodule Grades.CalculatorTest do
  use ExUnit.Case
  alias Grades.Calculator

  describe "percentage_grade/1" do
    test "sample" do
      assert 85 ==
               Calculator.percentage_grade(%{
                 homework: [0.8],
                 labs: [1, 1, 1],
                 midterm: 0.70,
                 final: 0.9
               })
    end
    test "pctg_gd_test1" do
		assert 80 ==
				Calculator.percentage_grade(%{
					homework: [1],
					labs: [0],
					midterm: 1,
					final: 1
				})
	end
	test "pctg_gd_test2" do
		assert 70 ==
				Calculator.percentage_grade(%{
					homework: [0],
					labs: [1, 1, 1],
					midterm: 1,
					final: 1
				})
	end
  end
end
