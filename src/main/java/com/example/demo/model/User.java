package com.example.demo.model;

public class User implements Comparable<User>  {

    private int id;
    private String name;

    public User(String name) {
               this.name = name;
    }
    public User (int id, String name) {
        this.id= id;
        this.name =name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(User o) {
        return Integer.compare(id, o.getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    public class Account {

        private String nameAccount;
        private int idAccount;
        private int balanceAccount;

        public Account(String nameAccount, int idAccount, int balanceAccount) {
            this.nameAccount = nameAccount;
            this.idAccount = idAccount;
            this.balanceAccount = balanceAccount;
        }

        public String getNameAccount() {
            return nameAccount;
        }

        public void setNameAccount(String nameAccount) {
            this.nameAccount = nameAccount;
        }

        public int getIdAccount() {
            return idAccount;
        }

        public void setIdAccount(int idAccount) {
            this.idAccount = idAccount;
        }

        public int getBalanceAccount() {
            return balanceAccount;
        }

        public void setBalanceAccount(int balanceAccount) {
            this.balanceAccount = balanceAccount;
        }
    }
}


