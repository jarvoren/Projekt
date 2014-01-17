package mpr.proj.pedigree;

public enum Sex {
    STALLION, MARE, GELDING;

	public static Sex valueOf(int int1) {
		switch (int1)
		{
		case 1:{return Sex.MARE;}
		case 2:{return Sex.STALLION; }
		case 3:{return Sex.GELDING;}
		}
		return null;
	}
}

