package com.bhargo.user.Expression.Interfaces;

import com.bhargo.user.pojos.PointWithDistance;

import java.util.List;

public interface SpatialFunctions
{
	public int getDistance(String Position1,String Position2);
	public boolean isWithinDistance(String currentPosition,List<String> referencePosition,double radius);
	public boolean isWithinBoundary(String currentPosition,List<String> ListofPositions);//pending
	public List<String> getNearbyValue(String currentPosition,List<String> ListofPositions,double NoofRecords);
	public List<PointWithDistance> getMultiColumnNearbyValue(String SelectedColumns, String currentPosition, List<String> ListofPositions, double NoofRecords);
	public List<String> getNearbyValueWithinRange(String currentPosition,List<String> ListofPositions,double NoofRecords,int RangeInMeters);
	public List<PointWithDistance> getMultiColumnNearbyValueWithinRange(String SelectedColumns,String currentPosition,List<String> ListofPositions,double NoofRecords,int RangeInMeters);
	public int getNearestDistance(String currentPosition,List<String> ListofPositions);

}
