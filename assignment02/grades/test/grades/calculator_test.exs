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
    test "pctg_gd_test_80" do
		assert 80 ==
				Calculator.percentage_grade(%{
					homework: [1],
					labs: [0],
					midterm: 1,
					final: 1
				})
	end
	test "pctg_gd_test_70" do
		assert 70 ==
				Calculator.percentage_grade(%{
					homework: [0],
					labs: [1, 1, 1],
					midterm: 1,
					final: 1
				})
	end
  end
  
  describe "letter_grade/1" do
	test "ltr_gd_test_EIN" do
		assert "EIN" ==
				Calculator.letter_grade(%{
					homework: [0],
					labs: [0],
					midterm: 0,
					final: 0
				})
	end
	test "ltr_gd_test_A+" do
		assert "A+" ==
				Calculator.letter_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 1,
					final: 1
				})
	end
	test "ltr_gd_test_A" do
		assert "A" ==
				Calculator.letter_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 1,
					final: 0.5
				})
	end
	test "ltr_gd_test_A-" do
		assert "A-" ==
				Calculator.letter_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 1,
					final: 0.35
				})
	end
	test "ltr_gd_test_B+" do
		assert "B+" ==
				Calculator.letter_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 1,
					final: 0.2
				})
	end
	test "ltr_gd_test_B" do
		assert "B" ==
				Calculator.letter_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 1,
					final: 0.1
				})
	end
	test "ltr_gd_test_C+" do
		assert "C+" ==
				Calculator.letter_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 0.9,
					final: 0
				})
	end
	test "ltr_gd_test_C" do
		assert "C" ==
				Calculator.letter_grade(%{
					homework: [0.7],
					labs: [1, 1, 1],
					midterm: 0.4,
					final: 0.4
				})
	end
	test "ltr_gd_test_D+" do
		assert "D+" ==
				Calculator.letter_grade(%{
					homework: [0.6],
					labs: [1, 1, 1],
					midterm: 0.4,
					final: 0.4
				})
	end
	test "ltr_gd_test_D" do
		assert "D" ==
				Calculator.letter_grade(%{
					homework: [0.4],
					labs: [1, 1, 1],
					midterm: 0.4,
					final: 0.4
				})
	end
	test "ltr_gd_test_E" do
		assert "E" ==
				Calculator.letter_grade(%{
					homework: [0.4],
					labs: [0.4, 0.4, 0.4],
					midterm: 0.4,
					final: 0.4
				})
	end
  end
  
  describe "numeric_grade/1" do
	test "nmc_gd_test_0" do
		assert 0 ==
				Calculator.numeric_grade(%{
					homework: [0],
					labs: [0],
					midterm: 0,
					final: 0
				})
	end
	test "nmc_gd_test_10" do
		assert 10 ==
				Calculator.numeric_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 1,
					final: 1
				})
	end
	test "nmc_gd_test_9" do
		assert 9 ==
				Calculator.numeric_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 1,
					final: 0.5
				})
	end
	test "nmc_gd_test_8" do
		assert 8 ==
				Calculator.numeric_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 1,
					final: 0.35
				})
	end
	test "nmc_gd_test_7" do
		assert 7 ==
				Calculator.numeric_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 1,
					final: 0.2
				})
	end
	test "nmc_gd_test_6" do
		assert 6 ==
				Calculator.numeric_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 1,
					final: 0.1
				})
	end
	test "nmc_gd_test_5" do
		assert 5 ==
				Calculator.numeric_grade(%{
					homework: [1],
					labs: [1, 1, 1],
					midterm: 0.9,
					final: 0
				})
	end
	test "nmc_gd_test_4" do
		assert 4 ==
				Calculator.numeric_grade(%{
					homework: [0.7],
					labs: [1, 1, 1],
					midterm: 0.4,
					final: 0.4
				})
	end
	test "nmc_gd_test_3" do
		assert 3 ==
				Calculator.numeric_grade(%{
					homework: [0.6],
					labs: [1, 1, 1],
					midterm: 0.4,
					final: 0.4
				})
	end
	test "nmc_gd_test_2" do
		assert 2 ==
				Calculator.numeric_grade(%{
					homework: [0.4],
					labs: [1, 1, 1],
					midterm: 0.4,
					final: 0.4
				})
	end
	test "nmc_gd_test_1" do
		assert 1 ==
				Calculator.numeric_grade(%{
					homework: [0.4],
					labs: [0.4, 0.4, 0.4],
					midterm: 0.4,
					final: 0.4
				})
	end
  end
end
