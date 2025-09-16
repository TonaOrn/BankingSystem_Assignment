package com.ig.banking_system.configuration;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.model.naming.ImplicitIndexNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

import java.util.stream.Collectors;

public class CustomPhysicalNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

	@Override
	public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
		// Check if a specific foreign key name is explicitly set
		if (source.getUserProvidedIdentifier() != null) {
			return source.getUserProvidedIdentifier();
		}

		// Generate a custom foreign key name
		String tableName = source.getTableName().getText();
		String columnName = source.getColumnNames().stream()
				.map(Identifier::getText)
				.collect(Collectors.joining("_"));

		String fkName = "fk_" + tableName + "_" + columnName;
		return Identifier.toIdentifier(fkName);
	}

	@Override
	public Identifier determineIndexName(ImplicitIndexNameSource source) {
		// Check if a specific index name is explicitly set
		if (source.getUserProvidedIdentifier() != null) {
			return source.getUserProvidedIdentifier();
		}

		// Generate a custom index name
		String tableName = source.getTableName().getText();
		String columnName = source.getColumnNames().stream()
				.map(Identifier::getText)
				.collect(Collectors.joining("_"));

		String indexName = "idx_" + tableName + "_" + columnName;
		return Identifier.toIdentifier(indexName);
	}
}