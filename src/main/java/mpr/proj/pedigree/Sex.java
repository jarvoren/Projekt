package mpr.proj.pedigree;

public enum Sex {
    STALLION, MARE, GELDING;

	public static Sex valueOf(int int1) {
		switch (int1)
		{
		case 1:{return Sex.GELDING; }
		case 2:{return Sex.MARE;}
		case 3:{return Sex.STALLION;}
		}
		return null;
	}
}

