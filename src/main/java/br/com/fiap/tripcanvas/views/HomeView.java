package br.com.fiap.tripcanvas.views;

import br.com.fiap.tripcanvas.activity.Activity;
import br.com.fiap.tripcanvas.activity.ActivityService;
import br.com.fiap.tripcanvas.activity.ActivityType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class HomeView extends VerticalLayout {

    private final ActivityService activityService;

    public HomeView(ActivityService activityService) {

        add(new H1("Trip Canvas"));
        add(new Paragraph("Planeje suas viagens com IA"));

        TextField textField = new TextField();
        textField.setLabel("Destino");
        textField.setClearButtonVisible(true);
        textField.setPrefixComponent(VaadinIcon.MAP_MARKER.create());
        add(textField);

        Button button = new Button("Gerar Roteiro", new Icon(VaadinIcon.MAGIC));
        add(button);
        button.addClickListener(e -> handleGenerate(textField.getValue()));

        this.activityService = activityService;
    }

    private void handleGenerate(String destination){
        var activities = activityService.generateActivityForDestination(destination);
        activities.forEach(activity -> add(new ActivityCardView(activity)));
    }

}
