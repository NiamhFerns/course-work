import math

def encrypt(word, n, e, start, pows):
	i = 2
	multiples = [word ** 2 % n]
	print(f"{word}^2 = {multiples[len(multiples) - 1]} mod({n})")
	while i <= start:
		multiples.append((multiples[len(multiples) - 1] ** 2) % n)
		print(f"{word}^{2**i} = {multiples[len(multiples) - 2]}^2 = {multiples[len(multiples) - 1]} mod({n})")
		i += 1
	
	multiples.insert(0, word)
	print()

	final_out = 1;
	for i in range (len(pows)):
		pows[i] *= multiples[i]
		if pows[i] != 0:
			print(f"({pows[i]})", end="")
		final_out *= pows[i] if pows[i] != 0 else 1

		if final_out > 100000:
			final_out %= n
	print()
	print()
	print(final_out)

def main():
	pows = []
	n = int(input("n: "))
	e = int(input("e: "))

	while e > 0:
		pows.append(1 if e % 2 == 1 else 0)
		e = e // 2
	pows.reverse
	start = (len(pows) - 1)
	print(start)
	
	userin = int(input("What word would you like to send? "))
	if userin > n:
		print(f"This is too long, your value needs to be less than {n}")
		return 1
	
	print(f"N = {n} E = {e}\nYour solution is...")
	encrypt(userin, n, e, start, pows)

main()
