package indi.ly.crush.mail.core.model;


import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * <h2>附件</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public class Annex
		implements Serializable {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	/**
	 * <p>
	 *     附件(<em>可以是多个</em>).
	 * </p>
	 */
	private List<File> annexList;

	public Annex(File file) {
		this(List.of(file));
	}
	
	public Annex(List<File> annexList) {
		Objects.requireNonNull(annexList)
				.stream()
				.filter(annex -> Objects.requireNonNull(annex).isDirectory())
				.forEachOrdered(annex -> {
					throw new RuntimeException("This is not a file, " + annex.getPath());
				});
		this.annexList = annexList;
	}
	
	public List<File> getAnnexList() {
		return annexList;
	}
	
	public void setAnnexList(List<File> annexList) {
		this.annexList = annexList;
	}
}
