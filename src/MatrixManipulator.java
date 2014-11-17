import java.util.HashMap;
import java.util.Scanner;


public class MatrixManipulator {
	
	public static HashMap<String, Matrix> matrices;
	
	public static void main(String[] args)
	{
		matrices = new HashMap<String, Matrix>();
		System.out.println("Welcome to MatrixManipulator!");
		String choice = "init";
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		String util_string = "init";
		Matrix util_matrix1 = new Matrix(1, 1, "util_1");
		Matrix util_matrix2 = new Matrix(1, 1, "util_2");
		while (!choice.equals("q"))
		{
			printMenu();
			choice = console.next();
			if (choice.equals("c"))
			{
				create();
			}
			if (choice.equals("p"))
			{
				System.out.print("Which matrix would you like to print? ");
				util_string = console.next();
				if (matrices.containsKey(util_string))
				{
					util_matrix1 = matrices.get(util_string);
					print(util_matrix1);
				}
				else
				{
					System.out.println("No matrix exists with the name '" + util_string + "'.");
				}
			}
			if (choice.equals("a"))
			{
				System.out.println("Which two matrices would you like to add?");
				System.out.print("Matrix 1: ");
				util_string = console.next();
				if (matrices.containsKey(util_string))
				{
					util_matrix1 = matrices.get(util_string);
					System.out.print("Matrix 2: ");
					util_string = console.next();
					if (matrices.containsKey(util_string))
					{
						util_matrix2 = matrices.get(util_string);
						System.out.print("What would you like to name the resulting matrix? ");
						util_string = console.next();
						add(util_matrix1, util_matrix2, util_string);
					}
					else
					{
						System.out.println("No matrix exists with the name '" + util_string + "'.");
					}
				}
				else
				{
					System.out.println("No matrix exists with the name '" + util_string + "'.");
				}
			}
			if (choice.equals("r"))
			{
				System.out.print("Which matrix would you like to row reduce? ");
				util_string = console.next();
				if (matrices.containsKey(util_string))
				{
					util_matrix1 = matrices.get(util_string);
					reduce(util_matrix1);
				}
			}
			if (choice.equals("d"))
			{
				System.out.print("Find the determinant of which matrix? ");
				util_string = console.next();
				if (matrices.containsKey(util_string))
				{
					util_matrix1 = matrices.get(util_string);
					System.out.println(determinant(util_matrix1));
				}
			}
		}
		System.out.println();
		System.out.println("Goodbye");
	}
	
	public static void printMenu()
	{
		System.out.println("[c]reate matrix  [p]rint matrix [a]dd matrices [r]ow reduce a matrix");
		System.out.println("[d]eterminant  [q]uit program");
		System.out.print("What would you like to do? ");
	}
	
	public static void create()
	{
		@SuppressWarnings("resource")
		Scanner console2 = new Scanner(System.in);
		String choice = "n";
		String name = "default";
		while (choice.equals("n"))
		{
			System.out.print("Matrix name........:");
			name = console2.next();
			if (matrices.containsKey(name))
			{
				System.out.print("A matrix with this name already exists, overwrite it? (y/n) ");
				choice = console2.next();
			}
			else
			{
				choice = "y";
			}
		}
		System.out.print("Number of rows.....: ");
		int rows = console2.nextInt();
		System.out.print("Number of columns..: ");
		int columns = console2.nextInt();
		float[][] init_data;
		init_data = new float[rows][columns];
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				System.out.print("Please enter element <" + (i + 1) + "," + (j + 1) + "> : ");
				init_data[i][j] = console2.nextFloat();
			}
		}
		Matrix matrix = new Matrix(rows, columns, init_data, name);
		matrices.put(name, matrix);
	}
	
	public static void print(Matrix init_matrix)
	{
		Matrix matrix = init_matrix;
		int length = 0;
		float num = 0;
		for (int i = 0; i < matrix.rows; i++)
		{
			for (int j = 0; j < matrix.columns; j++)
			{
				num = (float)Math.round(matrix.data[i][j] * 100000) / 100000;
				System.out.print(num);
				length = Float.toString(num).length();
				if (j % matrix.columns < matrix.columns - 1)
				{
					for (int k = 0; k < (matrix.longest - length); k++)
					{
						System.out.print(" ");
					}
				}
			}
			System.out.println();
		}
	}
	
	public static void add(Matrix matrix1, Matrix matrix2, String new_name)
	{
		if (matrix1.rows != matrix2.rows || matrix1.columns != matrix2.columns)
		{
			System.out.println("Matrices " + matrix1.name + " and " + matrix2.name + " have mismatching dimensions!");
		}
		else
		{
			float[][] init_data;
			init_data = new float[matrix1.rows][matrix1.columns];
			for (int i = 0; i < matrix1.rows; i++)
			{
				for (int j = 0; j < matrix1.columns; j++)
				{
					init_data[i][j] = matrix1.data[i][j] + matrix2.data[i][j];
				}
			}
			Matrix return_matrix = new Matrix(matrix1.rows, matrix1.columns, init_data, new_name);
			matrices.put(new_name, return_matrix);
			System.out.println(new_name + ":");
			print(return_matrix);
		}
	}
	
	public static float[] divideRow(float[] row, float data)
	{
		for (int i = 0; i < row.length; i++)
		{
			row[i] = row[i] / data;
		}
		return row;
	}
	
	public static float[] subtractRow(float[] sub, float[] init, float m)
	{
		for (int i = 0; i < init.length; i++)
		{
			init[i] = init[i] - m * sub[i];
		}
		return init;
	}
	
	public static void reduce(Matrix matrix)
	{
		Matrix temp = matrix;
		int rows = temp.rows;
		for (int i = 0; i < rows; i++)
		{
			temp.data[i] = divideRow(temp.data[i], temp.data[i][i]);
			for (int j = 0; j < rows; j++)
			{
				if (i != j)
				{
					temp.data[j] = subtractRow(temp.data[i], temp.data[j], temp.data[j][i]);
				}
			}
		}
		print(temp);
	}
	
	public static Matrix newMatrix(Matrix m, int row, int column)
	{
		float[][] temp = new float[m.rows - 1][m.columns - 1];
		for (int i = 0; i < m.rows; i++)
		{
			for (int j = 0; j < m.columns; j++)
			{
				if (i < row && j < column) {
					temp[i][j] = m.data[i][j];
				} else if (i < row && j > column)
				{
					temp[i][j - 1] = m.data[i][j];
				} else if (i > row && j < column)
				{
					temp[i - 1][j] = m.data[i][j];
				} else if (i > row && j > column)
				{
					temp[i - 1][j - 1] = m.data[i][j];
				}
			}
		}
		return new Matrix(m.rows - 1, m.columns - 1, temp, "temp");
	}
	
	public static float determinant(Matrix matrix)
	{
		if (matrix.rows != matrix.columns)
		{
			System.out.println("Determinant requires a square matrix.");
			return 0;
		} else if (matrix.rows == 2)
		{
			return matrix.data[0][0] * matrix.data[1][1] - matrix.data[0][1] * matrix.data[1][0];
		} else if (matrix.rows > 2)
		{
			float tot = 0;
			for (int i = 0; i < matrix.rows; i++)
			{
				tot += Math.pow(-1, i) * matrix.data[0][i] * determinant(newMatrix(matrix, 0, i));
			}
			return tot;
		}
		return 0;
	}
	
	// save method to save matrix as a text file
	
	// load method to load matrix from a text file
	
	// matrix exponent method
	
	// eigenvalue method (will be difficult)
	
	// delete method to delete single matrix
	
	// clear memory method to delete all matrices
	
	// method to test for rref
	
	// method to test matrix singularity
	
}
