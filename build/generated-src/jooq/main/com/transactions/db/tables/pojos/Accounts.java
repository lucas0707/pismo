/*
 * This file is generated by jOOQ.
 */
package com.transactions.db.tables.pojos;


import java.io.Serializable;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID accountId;
    private final String documentNumber;

    public Accounts(Accounts value) {
        this.accountId = value.accountId;
        this.documentNumber = value.documentNumber;
    }

    public Accounts(
        UUID accountId,
        String documentNumber
    ) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
    }

    /**
     * Getter for <code>public.accounts.account_id</code>.
     */
    public UUID getAccountId() {
        return this.accountId;
    }

    /**
     * Getter for <code>public.accounts.document_number</code>.
     */
    public String getDocumentNumber() {
        return this.documentNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Accounts other = (Accounts) obj;
        if (this.accountId == null) {
            if (other.accountId != null)
                return false;
        }
        else if (!this.accountId.equals(other.accountId))
            return false;
        if (this.documentNumber == null) {
            if (other.documentNumber != null)
                return false;
        }
        else if (!this.documentNumber.equals(other.documentNumber))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.accountId == null) ? 0 : this.accountId.hashCode());
        result = prime * result + ((this.documentNumber == null) ? 0 : this.documentNumber.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Accounts (");

        sb.append(accountId);
        sb.append(", ").append(documentNumber);

        sb.append(")");
        return sb.toString();
    }
}