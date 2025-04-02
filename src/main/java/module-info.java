module com.riotgames.conwaysgameoflife {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;

  opens com.riotgames.conwaysgameoflife to javafx.fxml;
  exports com.riotgames.conwaysgameoflife;
}