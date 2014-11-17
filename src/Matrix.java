public class Matrix {
	
	int rows;
	int columns;
	float[][] data;
	String name;
	int longest;
	
	public Matrix(int init_rows, int init_columns, String init_name)
	{
		rows = init_rows;
		columns = init_columns;
		float[][] data = new float[rows][columns];
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				data[i][j] = 0;
			}
		}
		name = init_name;
	}
	
	public Matrix(int init_rows, int init_columns, float[][] init_data, String init_name)
	{
		rows = init_rows;
		columns = init_columns;
		data = init_data;
		name = init_name;
		int temp = 0;
		int temp_longest = 0;
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				temp = (int) (Float.toString(data[i][j]).length());
				if (temp > temp_longest)
				{
					temp_longest = temp;
				}
			}
		}
		longest = temp_longest + 2;
	}
}
