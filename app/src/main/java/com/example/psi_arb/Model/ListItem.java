package com.example.psi_arb.Model;

//************************************************************
// This is the List of arbitrage spread which will populate
// the RecyclerView. The data should list the symbol, bid, ask
// arb spread, and exchanges.
//************************************************************

public class ListItem {

    private String name;
    private String content;

    public ListItem(String name, String content) {
        this.name = name;
        this.content = content;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
