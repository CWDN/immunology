package piefarmer.immunology.disease;

import java.util.List;

public class DiseaseMeasles extends Disease {

	public DiseaseMeasles(int par1, int par2, int par3, List<Integer> list,
			String diseasename) {
		super(par1, par2, 0, list, diseasename);
	}
	public DiseaseMeasles(int par1, int par2, int par3, List<Integer> list,
			String diseasename, boolean b) {
		super(par1, par2, 0, list, diseasename, b);
	}
	public DiseaseMeasles(Disease disease) {
		super(disease.getdiseaseID(), disease.getDuration(), 0, disease.DiseaseEffects, disease.getName());
	}

}
