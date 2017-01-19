package com.rooney.kyro;

import java.util.Collections;
import java.util.List;
import java.util.Map;

//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/*
 * has default ctor
 * doesn't use final instance vars
 * do not build with guava immutable map
 */
public class LoadBatchRequestVOKyroFriendly {

	public LoadBatchRequestVOKyroFriendly() {
		super();
	}

	// final
	private String loadInstanceId;

	// final
	private String batchIdentifier;

	private List<Map<String, Object>> items = Lists.newArrayList();

	private List<Map<String, Object>> oldItems = Lists.newArrayList();

	// final
	private LoaderAction loaderAction;

	private LoadBatchRequestVOKyroFriendly(String loadInstanceId, String batchIdentifier, LoaderAction loaderAction) {
		this.loadInstanceId = loadInstanceId;
		this.batchIdentifier = batchIdentifier;
		this.loaderAction = loaderAction;
	}

	public static final LoadBatchRequestVOKyroFriendly create(String loadInstanceId, String batchIdentifier,
			LoaderAction loaderAction, List<Map<String, Object>> items) {
		return create(loadInstanceId, batchIdentifier, loaderAction, items,
				Collections.<Map<String, Object>>emptyList());
	}

	// @JsonCreator
	// public static final LoadBatchRequestVO create(@JsonProperty("loadInstanceId") String loadInstanceId,
	// @JsonProperty("batchIdentifier") String batchIdentifier,
	// @JsonProperty("loaderAction") LoaderAction loaderAction,
	// @JsonProperty("items") List<Map<String, Object>> items,
	// @JsonProperty("oldItems") List<Map<String, Object>> oldItems) {
	public static final LoadBatchRequestVOKyroFriendly create(String loadInstanceId, String batchIdentifier,
			LoaderAction loaderAction, List<Map<String, Object>> items, List<Map<String, Object>> oldItems) {

		// For backwards compability we allow a null LoaderAction
		if (loaderAction == null) {
			loaderAction = LoaderAction.INSERT;
		}

		LoadBatchRequestVOKyroFriendly loadBatchRequestVO = new LoadBatchRequestVOKyroFriendly(loadInstanceId, batchIdentifier, loaderAction);
		loadBatchRequestVO.setItems(items);
		loadBatchRequestVO.setOldItems(oldItems);
		return loadBatchRequestVO;
	}

	public String getLoadInstanceId() {
		return loadInstanceId;
	}

	public String getBatchIdentifier() {
		return batchIdentifier;
	}

	public List<Map<String, Object>> getItems() {
		return Collections.unmodifiableList(items);
	}

	public List<Map<String, Object>> addItems(List<Map<String, Object>> items) {
		this.items.addAll(items);
		return getItems();
	}

	public void setItems(List<Map<String, Object>> items) {
		this.items = items;
	}

	public LoaderAction getLoaderAction() {
		return loaderAction;
	}

	public List<Map<String, Object>> getOldItems() {
		return Collections.unmodifiableList(oldItems);
	}

	public void setOldItems(List<Map<String, Object>> oldItems) {
		this.oldItems = oldItems;
	}

	public int hashCode() {
		return Objects.hashCode(loadInstanceId, batchIdentifier, items, oldItems, loaderAction);
	}

	public boolean equals(Object object) {
		if (object instanceof LoadBatchRequestVOKyroFriendly) {
			LoadBatchRequestVOKyroFriendly that = (LoadBatchRequestVOKyroFriendly) object;
			return Objects.equal(this.loadInstanceId, that.loadInstanceId)
					&& Objects.equal(this.batchIdentifier, that.batchIdentifier)
					&& Objects.equal(this.items, that.items) && Objects.equal(this.oldItems, that.oldItems)
					&& Objects.equal(this.loaderAction, that.loaderAction);
		}
		return false;
	}

	public String toString() {
		return MoreObjects.toStringHelper(this).add("loadInstanceId", loadInstanceId)
				.add("batchIdentifier", batchIdentifier).add("items", items).add("oldItems", oldItems)
				.add("loaderAction", loaderAction).toString();
	}

}
