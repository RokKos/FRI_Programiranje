from string import ascii_lowercase
from datetime import datetime
from time import mktime
from random import seed, randint

current_secconds = int(mktime(datetime.now().timetuple()))
seed(current_secconds)

OPERATOR_AND = "AND";
OPERATOR_OR = "OR";
OPERATOR_NOT = "NOT";
CONSTANT_TRUE = "TRUE";
CONSTANT_FALSE = "FALSE";
OPERATOR_L_BRACKET = '(';
OPERATOR_R_BRACKET = ')';

possible_operators = [OPERATOR_NOT, OPERATOR_AND, OPERATOR_OR]
possible_seperators = [OPERATOR_L_BRACKET, OPERATOR_R_BRACKET]
posible_const_values = [CONSTANT_FALSE, CONSTANT_TRUE]

MAX_VARIABLES = len(ascii_lowercase)
MAX_OPERATORS = 100000

def pick_number_of_var_op():
	var_num = randint(1, MAX_VARIABLES)
	op_num = randint(var_num - 1, MAX_OPERATORS)
	return (var_num, op_num)

def pick_variables(var_num):
	return list(ascii_lowercase[0:var_num])

def is_operator(element):
	return element in (possible_operators + possible_seperators)

def determine_pool(element, possible_values, l_bracket_count):
	if (element == OPERATOR_NOT):
		return possible_values + [OPERATOR_L_BRACKET] + posible_const_values

	if(element == OPERATOR_L_BRACKET):
		return possible_values + [OPERATOR_L_BRACKET] + [OPERATOR_NOT] + posible_const_values

	if(element == OPERATOR_R_BRACKET):
		return possible_operators[1:]

	if (element in possible_operators[1:]):
		return possible_values + posible_const_values + [OPERATOR_NOT] + [OPERATOR_L_BRACKET]

	if (element in (possible_values + posible_const_values)):
		ret_arr = possible_operators[1:]
		if (l_bracket_count > 0):
			ret_arr += OPERATOR_R_BRACKET

		return ret_arr

	return []  # Error

def add_to_string(element, output_string):
	if(element in OPERATOR_L_BRACKET):
		return output_string + element

	if(element in OPERATOR_R_BRACKET):
		return output_string[:-1] + element + " "

	return output_string + element + " "


def check_for_bracket(element):
	if (element == OPERATOR_L_BRACKET): return 1
	if (element == OPERATOR_R_BRACKET): return -1
	return 0;

if __name__ == "__main__":
	var_num, op_num = pick_number_of_var_op()
	possible_values = pick_variables(var_num)

	l_bracket_count = 0
	pool_of_possibilities = possible_values + [OPERATOR_NOT] + [OPERATOR_L_BRACKET] + posible_const_values

	output_string = ""
	used_variables = set()

	while (var_num > 0 and op_num > 0):
		#print(pool_of_possibilities)
		el_index = randint(0, len(pool_of_possibilities) - 1)
		element = pool_of_possibilities[el_index]

		output_string = add_to_string(element, output_string)

		l_bracket_count += check_for_bracket(element)


		pool_of_possibilities = determine_pool(element, possible_values, l_bracket_count)
		if (is_operator(element)): op_num -= 1
		else:
			if (element not in used_variables):
				used_variables.add(element)
				var_num -= 1


	for i in range(l_bracket_count):
		output_string = add_to_string(OPERATOR_R_BRACKET, output_string)
	print (output_string)

