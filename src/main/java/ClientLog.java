import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ClientLog {
    protected int amount;
    protected int productNum;
    protected List<ClientLog> logNumber = new ArrayList<>();

    public ClientLog(int productNum, int amount) {
        this.productNum = productNum;
        this.amount = amount;
    }

    public void log(int productNum, int amount) {
        ClientLog clientLog = new ClientLog(productNum + 1, amount);
        logNumber.add(clientLog);
    }

    public void exportAsCSV(File txtFile) {

        if (!txtFile.exists()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
                StringJoiner namesProducts = new StringJoiner(",")
                        .add("productNum")
                        .add("amount");
                String[] header = namesProducts.toString().split(",");
                writer.writeNext(header);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ColumnPositionMappingStrategy<ClientLog> strategy =
                new ColumnPositionMappingStrategy<>();
        strategy.setType(ClientLog.class);
        strategy.setColumnMapping("productNum", "amount");

        try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(txtFile, true))
                .build()) {

            StatefulBeanToCsv<ClientLog> sbc = new StatefulBeanToCsvBuilder<ClientLog>(writer)
                    .withMappingStrategy(strategy)
                    .build();

            sbc.write(logNumber.iterator());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }
}
