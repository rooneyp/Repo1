package com.rooney.Mess.aemess;

import java.util.ArrayList;
import java.util.List;

public class AeKernalTypes {
	
	class AeEngine {
		Pipeline pipeLine = new Pipeline() {
			List<Step> steps = new ArrayList<Step>();
			
			public List<Step> getSteps() {
				return steps;
			}
			
			public void add(int position, Step step) {
				steps.add(position, step);
			}
			
			public void add(Step step) {
				steps.add(step);
			}
		};
		
		AEDataType process(AEDataType aEDataType) {
			List<Step> steps = pipeLine.getSteps();
			AEDataType aEDataTypeInProgress = aEDataType;
			for (Step step : steps) {
//				aEDataTypeInProgress = step.preProcess(aEDataTypeInProgress);
				aEDataTypeInProgress = step.process(aEDataTypeInProgress);
//				aEDataTypeInProgress = step.postProcess(aEDataTypeInProgress);
			}
			return aEDataTypeInProgress;
		}
		
	}
	
	/** an ordered collection of steps **/
	interface Pipeline {
		/** Add Step **/
		void add(Step step);
		
		/** Add Step in position in Pipeline**/
		void add(int position, Step step);
		
		/** get list of Steps in order **/ 
		List<Step> getSteps();
		
	}
	
	/** A generic Step which performs processing **/
	interface Step {
//		AEDataType preProcess(AEDataType aEDataType);
		AEDataType process(AEDataType aEDataType);
//		AEDataType postProcess(AEDataType aEDataType);
	}
	
	/** A consumer of records produced by the Agg Engine **/ 
	interface AEOutputConsumer {
		void consume(AEDataType aEDataType);
	}
	
	interface AEDataType {
		List<AEElementType> getElements();
	}

	
	interface AEElementType {
	}
}
