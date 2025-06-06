package ca.georgiancollege.ice5

import android.widget.Button
import ca.georgiancollege.ice5.databinding.ActivityMainBinding

class Calculator(private var binding: ActivityMainBinding)
{
    // Lists of buttons for easy access
    private lateinit var numberButtons: List<Button>
    private lateinit var operatorButtons: List<Button>
    private lateinit var modifierButtons: List<Button>

    // Properties for calculation logic
    private var currentOperand: Float? = null
    private var currentOperator: String? = null
    private var operatorSelected = false

    init {
        initializeButtonLists(binding)


        modifierButtons = listOf(
            binding.plusMinusButton,
            binding.clearButton, binding.deleteButton,
            binding.equalsButton
        )

        configureNumberInput()
        configureModifierButtons()
        configureOperatorButtons()
    }

    private fun initializeButtonLists(binding: ActivityMainBinding)
    {
        // Initialize number buttons
        numberButtons = listOf(
            binding.zeroButton, binding.oneButton, binding.twoButton,
            binding.threeButton, binding.fourButton, binding.fiveButton,
            binding.sixButton, binding.sevenButton, binding.eightButton,
            binding.nineButton, binding.decimalButton
        )

        // Initialize operator buttons
        operatorButtons = listOf(
            binding.plusButton, binding.minusButton,
            binding.multiplyButton, binding.divideButton, binding.percentButton
        )


    }

    private fun configureNumberInput()
    {
        numberButtons.forEach { button ->
            button.setOnClickListener {
                val input = button.text.toString()
                val currentResultText = binding.resultEditText.text.toString()

                // Prevent multiple decimal points in the current number
                if (input == "." && currentResultText.contains("."))
                {
                    return@setOnClickListener
                }

                // Replace 0 if input is not "."; otherwise, append
                if (currentResultText == "0" && input != ".")
                {
                    binding.resultEditText.setText(input)
                }
                else
                {
                    binding.resultEditText.append(input)
                }
            }
        }
    }

    private fun configureModifierButtons()
    {
        modifierButtons.forEach { button ->
            button.setOnClickListener {
                when (button)
                {
                    binding.clearButton -> {
                        binding.resultEditText.setText("0")
                        currentOperand = null
                        currentOperator = null
                        operatorSelected = false
                    }

                    binding.deleteButton -> {
                        val currentText = binding.resultEditText.text.toString()
                        if (currentText.isNotEmpty()) {
                            val newText = currentText.dropLast(1)
                            if (newText == "-" || newText.isEmpty()) {
                                binding.resultEditText.setText("0")
                            } else {
                                binding.resultEditText.setText(newText)
                            }
                        }
                    }

                    binding.plusMinusButton -> {
                        val currentText = binding.resultEditText.text.toString()
                        if (currentText == "0" || currentText.isEmpty()) return@setOnClickListener
                        if (currentText.startsWith("-")) {
                            binding.resultEditText.setText(currentText.removePrefix("-"))
                        } else {
                            binding.resultEditText.setText("-$currentText")
                        }
                    }

                    binding.equalsButton -> {
                        val secondOperand = binding.resultEditText.text.toString().toFloatOrNull()

                        if (currentOperand != null && secondOperand != null && currentOperator != null) {
                            val result = when (currentOperator) {
                                "+" -> currentOperand!! + secondOperand
                                "-" -> currentOperand!! - secondOperand
                                "*" -> currentOperand!! * secondOperand
                                "/" -> if (secondOperand != 0f) currentOperand!! / secondOperand else {
                                    binding.resultEditText.setText("Error")
                                    return@setOnClickListener
                                }
                                else -> return@setOnClickListener
                            }

                            binding.resultEditText.setText(result.toString())
                            currentOperand = null
                            currentOperator = null
                            operatorSelected = false
                        }
                    }
                }
            }
        }
    }

    private fun configureOperatorButtons()
    {
        operatorButtons.forEach { button ->
            button.setOnClickListener {
                if (operatorSelected) return@setOnClickListener

                val currentText = binding.resultEditText.text.toString()
                val operand = currentText.toFloatOrNull()

                if (operand != null) {
                    currentOperand = operand
                    currentOperator = when (button) {
                        binding.plusButton -> "+"
                        binding.minusButton -> "-"
                        binding.multiplyButton -> "*"
                        binding.divideButton -> "/"
                        else -> null
                    }

                    operatorSelected = true
                    binding.resultEditText.setText("0")
                }
            }
        }
    }
}
