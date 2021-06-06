import org.dieschnittstelle.mobile.android.skeleton.model.DataItem;

import java.util.List;
import java.util.function.Consumer;

public interface IDataItemCRUDOperationsAsync  {

        public void createDataItem(DataItem item, Consumer<DataItem> onCreated);

        public List<DataItem> readAllDataItems(Consumer<List<DataItem>> onRead);

        public DataItem readDataItem(long id, Consumer<DataItem> onRead);

        public DataItem updateDataItem(DataItem item, Consumer<DataItem> onUpdated);

        public void deleteDataItem(long id, Consumer<Boolean> onDeleted);

}

