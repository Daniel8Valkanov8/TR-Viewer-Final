package partView.diagrams;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import partBiology.Gene;
import partBiology.Transposon;
import dto.type.CircleCategoryDTO;
import dto.util.MyBeanUtils;
import partBiology.service.Service;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ViewChoosedGeneGraphic extends JFrame {
    private Service service;
    private List<String> parseList;
    private Set<CircleCategoryDTO> circleCategoryDTOs = new TreeSet<>();
    private Color[] coloursForCategory = new Color[]{
            XChartSeriesColors.BLUE,
            XChartSeriesColors.RED,
            XChartSeriesColors.GREEN,
            XChartSeriesColors.YELLOW,
            XChartSeriesColors.PURPLE
    };
    public ViewChoosedGeneGraphic(Service service, JList<String> list) {
        super();
        this.service = service;
        this.setSize(800, 600);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        this.setTitle("Choosed Gene");
        this.setContentPane(panel);
        this.parseList = MyBeanUtils.convertToList(list);
        createDTOsForChart();
        this.setVisible(true);
        PieChart chart = new PieChartBuilder().width(800).height(600).title("").build();
        setValuesOfDTOs();
        panel.add(new XChartPanel<>(chart));
        chart.getStyler().setCircular(true);
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setPlotContentSize(0.9);
        chart.getStyler().setPlotBackgroundColor(Color.DARK_GRAY);
        chart.getStyler().setSeriesColors(coloursForCategory);
        for (CircleCategoryDTO circleCategoryDTO : this.circleCategoryDTOs) {
            if (circleCategoryDTO.getValue()!=0) {
                chart.addSeries(circleCategoryDTO.getCategory(), circleCategoryDTO.getValue().intValue());
            }
        }

    }

    private void createDTOsForChart() {
        CircleCategoryDTO dnaDTO = new CircleCategoryDTO();
        dnaDTO.setCategory("DNA");
        dnaDTO.setColor(XChartSeriesColors.BLUE);
        circleCategoryDTOs.add(dnaDTO);
        CircleCategoryDTO ltrDTO = new CircleCategoryDTO();
        ltrDTO.setCategory("LTR");
        ltrDTO.setColor(XChartSeriesColors.GREEN);
        circleCategoryDTOs.add(ltrDTO);
        CircleCategoryDTO lineDTO = new CircleCategoryDTO();
        lineDTO.setCategory("LINE");
        lineDTO.setColor(XChartSeriesColors.PURPLE);
        circleCategoryDTOs.add(lineDTO);
        CircleCategoryDTO sineDTO = new CircleCategoryDTO();
        sineDTO.setCategory("SINE");
        sineDTO.setColor(XChartSeriesColors.YELLOW);
        circleCategoryDTOs.add(sineDTO);
        CircleCategoryDTO otherDTO = new CircleCategoryDTO();
        otherDTO.setCategory("OTHER");
        otherDTO.setColor(XChartSeriesColors.RED);
        circleCategoryDTOs.add(otherDTO);
    }

    private void setValuesOfDTOs() {

        Set<Gene> myChosenGenes = new TreeSet<>();
        for (String gene : parseList) {
            Gene current = service.getRepository().findGene(gene);
            myChosenGenes.add(current);
        }
        //todo
        long copiesInType = 0;
        for (Gene myChosenGene : myChosenGenes) {
            for (Transposon transposon : myChosenGene.getTransposons()) {


            }
        }
    }

    private CircleCategoryDTO findDTOWithThisCathegory(String type) {
        for (CircleCategoryDTO circleCategoryDTO : circleCategoryDTOs) {
            if (circleCategoryDTO.getCategory().equals(type)) {
                return circleCategoryDTO;
            }
        }
        return null;
    }
}
