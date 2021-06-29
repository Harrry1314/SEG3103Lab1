defmodule Grades.Calculator do
  def percentage_grade(mark) do
	Enum.random(0..100)
  end

  def letter_grade(mark) do
	Enum.random(["A+","A","A-","B+","B","C+","C","D+","D","E","F","EIN"])
  end

  def numeric_grade(mark) do
	Enum.random(1..10)
  end
end
