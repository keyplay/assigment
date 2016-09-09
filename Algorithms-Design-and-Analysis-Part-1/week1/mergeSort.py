#! python2.7
# mergeSort.py - input a list, return sorted list

def mergeSort(inputList):
	n = len(inputList)
	
	if n < 2:
		return inputList
	out = []
	left = mergeSort(inputList[:n//2])
	right = mergeSort(inputList[n//2:])
	i = 0
	j = 0
	while (len(left) > i)and(len(right) > j):
		if left[i] > right[j]:
			out.append(right[j])
			j += 1
		else:
			out.append(left[i])
			i += 1
	
	if len(left) > i:
		out += left[i:]
	if len(right) > j:
		out += right[j:]	
	return out
