# 21007069
# (1)
# A)
disp("Question 1A")
A = [0 0 7; 0 6 9]
B = zeros(3, 2)
time = fix(clock())
B(1, 1) = time(1)
B(1, 2) = time(2)
B(2, 1) = time(3)
B(2, 2) = time(4)
B(3, 1) = time(5)
B(3, 2) = time(6)

# B)
disp("Question 1B")
C = A * B;
C

# C)
disp("Question 1C")
disp("D = C^5")
D = C^5;
D
disp("Rank(D)")
rank(D)

# (2)
# A)
disp("Question 2A")
A = zeros(6,6);
for i = 1:6
  for j = 1:6
    A(i, j) = j - i;
  endfor
endfor
A
# B)
disp("Question 2B")
disp("Rank(A)")
rank(A)

disp("RREF(A)")
rrefA = rref(A)

rowSpace = A([1, 2], :)'
colSpace = A'([1, 2], :)'

# (3)
# A)
disp("Question 3A")
M = [16 -8 4 -2 1;
     1  -1 1 -1 1;
     0   0 0  0 1;
     1   1 1  1 1;
     16  8 4  2 1];
b = [47; 8; 1; 2; 11];

disp("RREF of M")
rref(M)
disp("Solution to M")
M\b

x = (-2:2);
fplot(@(x) ((x.^4) - 2*(x.^3) + 3 * (x.^2) - x + 1));
title("Ferns 21007069");
ylabel("Y-Axis");
xlabel("X-Axis");

# (4)
# (B)
disp("Question 4B")
a = randi([1 5], 4, 1)
b = randi([1 5], 4, 1)

a * b'
rank(ans)
