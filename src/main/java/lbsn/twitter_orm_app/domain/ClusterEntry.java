package lbsn.twitter_orm_app.domain;

public class ClusterEntry {
	private String id;
	private int cluster;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCluster() {
		return cluster;
	}
	public void setCluster(int cluster) {
		this.cluster = cluster;
	}
}
