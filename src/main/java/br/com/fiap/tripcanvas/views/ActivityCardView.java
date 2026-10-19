package br.com.fiap.tripcanvas.views;

import br.com.fiap.tripcanvas.activity.Activity;
import br.com.fiap.tripcanvas.activity.ActivityType;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.badge.BadgeVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ActivityCardView extends Card {

    public ActivityCardView(Activity activity){
        setTitle(new Div(activity.getTitle()));
        add(new Paragraph(activity.getDescription()));
        setMedia(getIconFromType(activity.getType()));

        Badge badge = new Badge(activity.getDurationInMinutes() + "min");
        badge.addThemeVariants(BadgeVariant.SUCCESS);
        setHeaderSuffix(badge);

        addThemeVariants(CardVariant.HORIZONTAL);
        addThemeVariants(CardVariant.ELEVATED);

    }

    private Component getIconFromType(ActivityType type) {
        return switch (type){
            case MUSEUM -> VaadinIcon.BUILDING.create();
            case RESTAURANT -> VaadinIcon.CUTLERY.create();
            case HOTEL -> VaadinIcon.BED.create();
            case PARK -> VaadinIcon.BUG.create();
            case SHOPPING -> VaadinIcon.SHOP.create();
        };
    }


}
