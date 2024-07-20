package partView.elements;

import dto.util.MyBeanUtils;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SearchToolBarImpl extends JToolBar {
    private List<String> searchIn;
    private SearchListener searchListener;
    private Set<String> searchResult;
    public SearchToolBarImpl(JList<String> input, SearchListener searchListener) {
        super();
        this.searchIn = MyBeanUtils.convertToList(input);
        this.searchListener = searchListener;
        this.searchResult= new TreeSet<>();
        //elements in toolBar
        JLabel search = new JLabel("Search: ");
        JTextField searchField = new JTextField(10);
        this.add(search);
        this.add(searchField);

        //implementationOrSearch
        implementationOfSearch(searchField);
    }

    private void implementationOfSearch(JTextField searchField) {
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
                // Не се използва за текстови полета без ограничения на въвеждането
            }

            private void search() {
                String searchText = searchField.getText().toLowerCase();
                searchResult.clear(); // Изчистване на предишните резултати

                if (searchText.isEmpty()) {
                    searchResult.addAll(searchIn); // Ако полето за търсене е празно, използвайте пълен списък
                } else {
                    // Търсене на съвпадения в елементите от списъка
                    for (String item : searchIn) {
                        if (item.toLowerCase().startsWith(searchText)) { // Проверка дали елементът започва с търсения текст
                            searchResult.add(item); // Добавяне на съвпадение в резултатите
                        }
                    }
                }

                searchListener.onSearchUpdate(searchResult); // Извикване на метода на обратния извиквател за актуализиране на резултатите
            }


        });
    }

    public Set<String> getSearchResult() {
        return searchResult;
    }

}
