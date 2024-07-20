package partView.elements;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchToolBarMainImpl extends JToolBar {
    private List<String> searchIn;
    private SearchListener searchListener;
    private JTextField searchField;

    public SearchToolBarMainImpl(SearchListener searchListener) {
        super();
        this.searchListener = searchListener;
        // Елементи в лентата с инструменти
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(10);
        this.add(searchLabel);
        this.add(searchField);

        implementSearch();
    }

    private void implementSearch() {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }

            private void search() {
                String searchText = searchField.getText().toLowerCase();
                List<String> searchResult;

                if (searchText.isEmpty()) {
                    searchResult = searchIn;
                } else {
                    searchResult = searchIn.stream()
                            .filter(item -> item.toLowerCase().contains(searchText))
                            .collect(Collectors.toList());
                }

                searchListener.onSearchUpdate(searchResult.stream().collect(Collectors.toSet()));
            }
        });
    }

    public void setSearchInList(List<String> searchIn) {
        this.searchIn = searchIn;
    }
}

