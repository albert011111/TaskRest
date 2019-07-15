package com.kruczek.wallet.bill.model;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

public enum BillType {
	RENTAL("Czynsz"),
	WATER("Woda"),
	ELECTRICITY("Prąd"),
	FUEL("Paliwo"),
	FOOD("Jedzenie"),
	SEWAGE("Ścieki"),
	EXTRA("Inne");

	private static Map<String, BillType> FORMAT_MAP = Stream
			.of(BillType.values())
			.collect(Collectors.toMap(billType -> billType.billDesc, Function.identity()));
	private final String billDesc;

	BillType(String billDesc) {
		this.billDesc = billDesc;
	}

	@JsonCreator
	public static BillType fromString(String billTypeDescription) {
		Objects.requireNonNull(billTypeDescription, getNpeDescription("billTypeDescription"));
		return Optional
				.ofNullable(FORMAT_MAP.get(billTypeDescription))
				.orElseThrow(() -> new IllegalArgumentException(billTypeDescription));
	}

	public String getBillDesc() {
		return billDesc;
	}
}
