package org.liquidengine.legui.component.misc.listener.component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.FocusEvent;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.processor.EventProcessor;
import org.liquidengine.legui.system.context.Context;
import org.lwjgl.glfw.GLFW;

/**
 * Created by ShchAlexander on 11.11.2017.
 */
public class TabKeyEventListener implements EventListener<KeyEvent> {

    private Comparator<? super Component> comparator = Comparator.comparingInt((Component::getTabIndex));

    /**
     * Used to handle specific event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(KeyEvent event) {
        if (event.getComponent() == null) {
            return;
        }
        if (event.getKey() == GLFW.GLFW_KEY_TAB && event.getAction() != GLFW.GLFW_PRESS) {
            boolean controlPressed = (event.getMods() & GLFW.GLFW_MOD_CONTROL) != 0;
            boolean shiftPressed = (event.getMods() & GLFW.GLFW_MOD_SHIFT) != 0;
            if (controlPressed && !shiftPressed) {
                Component next = findNext(event.getComponent());
                // if ((next == null || next == component) && cycled) next = event.getContext().getFrame().getContainer();
                moveToNextTabFocusableComponent(event.getContext(), event.getComponent(), next, event.getFrame());
            } else if (controlPressed && shiftPressed) {
                Component prev = findPrev(event.getComponent());
                moveToNextTabFocusableComponent(event.getContext(), event.getComponent(), prev, event.getFrame());
            }
        }
    }

    /**
     * Used to find previous component (components sorted by tab index).
     *
     * @param component current component.
     *
     * @return previous component.
     */
    private Component findPrev(Component component) {
        if (!component.isVisible()) {
            return null;
        }

        Component prev = null;
        if (component.isTabFocusable()) {
            prev = component;
        }
        prev = findPrevInParent(component, component.getParent(), prev);
        return prev;

    }

    /**
     * Used to find previous component in parent (in neighbors).
     *
     * @param component current component.
     * @param parent parent component.
     * @param prev current previous component.
     *
     * @return previous component.
     */
    private Component findPrevInParent(Component component, Component parent, Component prev) {
        if (parent == null) {
            return prev;
        }

        List<Component> childs = parent.getChilds();
        childs.sort(comparator);
        Collections.reverse(childs);

        int index = childs.indexOf(component);
        if (index != childs.size() - 1) {
            for (int i = index + 1; i < childs.size(); i++) {
                Component child = childs.get(i);
                if (child.isVisible()) {
                    if (child.isTabFocusable()) {
                        prev = child;
                        if (!child.isEmpty()) {
                            prev = findPrevInChilds(child.getChilds(), prev);
                        }
                        return prev;
                    } else {
                        if (!child.isEmpty()) {
                            Component cprev = findPrevInChilds(child.getChilds(), prev);
                            if (prev != cprev) {
                                return cprev;
                            }
                        }
                    }
                }
            }
            prev = findPrevInParent(parent, parent.getParent(), prev);
        } else {
            if (parent.isTabFocusable()) {
                prev = parent;
                return prev;
            } else {
                prev = findPrevInParent(parent, parent.getParent(), prev);
            }
        }
        return prev;
    }

    /**
     * Used to find previous component in child components.
     *
     * @param childs child components.
     * @param prev current previous component.
     *
     * @return previous component.
     */
    private Component findPrevInChilds(List<Component> childs, Component prev) {
        childs.sort(comparator);
        Collections.reverse(childs);
        for (Component child : childs) {
            if (child.isVisible()) {
                if (child.isTabFocusable()) {
                    prev = child;
                    if (!child.isEmpty()) {
                        prev = findPrevInChilds(child.getChilds(), prev);
                    }
                    return prev;
                } else {
                    if (!child.isEmpty()) {
                        Component cprev = findPrevInChilds(child.getChilds(), prev);
                        if (prev != cprev) {
                            return cprev;
                        }
                    }
                }
            }
        }

        return prev;
    }

    /**
     * Used to find next component (components sorted by tab index).
     *
     * @param component current component.
     *
     * @return next component.
     */
    private Component findNext(Component component) {
        if (!component.isVisible()) {
            return null;
        }

        Component next = null;
        if (component.isTabFocusable()) {
            next = component;
        }
        if (component.isEmpty()) {
            next = findNextInParent(component, component.getParent(), next);
        } else {
            next = findNextInChilds(component.getChilds(), next);
            if (next == component) {
                next = findNextInParent(component, component.getParent(), next);
            }
        }
        return next;
    }

    /**
     * Used to find next component in child components.
     *
     * @param childs child components.
     * @param next current next component.
     *
     * @return next component.
     */
    private Component findNextInChilds(List<Component> childs, Component next) {
        if (childs.isEmpty()) {
            return next;
        }

        childs.sort(comparator);

        for (Component child : childs) {
            if (child.isVisible()) {
                if (child.isTabFocusable()) {
                    next = child;
                    return next;
                } else {
                    if (!child.isEmpty()) {
                        Component cnext = findNextInChilds(child.getChilds(), next);
                        if (next != cnext) {
                            return cnext;
                        }
                    }
                }
            }
        }

        return next;
    }

    /**
     * Used to find next component in parent (in neighbors).
     *
     * @param component current component.
     * @param parent parent component.
     * @param next current next component.
     *
     * @return next component.
     */
    private Component findNextInParent(Component component, Component parent, Component next) {
        if (parent == null) {
            return next;
        }

        List<Component> childs = parent.getChilds();
        childs.sort(comparator);

        int index = childs.indexOf(component);
        if (index != childs.size() - 1) {
            for (int i = index + 1; i < childs.size(); i++) {
                Component child = childs.get(i);
                if (child.isVisible()) {
                    if (child.isTabFocusable()) {
                        next = child;
                        return next;
                    } else {
                        if (!child.isEmpty()) {
                            Component cnext = findNextInChilds(child.getChilds(), next);
                            if (next != cnext) {
                                return cnext;
                            }
                        }
                    }
                }
            }
            next = findNextInParent(parent, parent.getParent(), next);
        } else {
            next = findNextInParent(parent, parent.getParent(), next);
        }

        return next;
    }

    /**
     * Used to move to found focusable component.
     *
     * @param context context (used in {@link FocusEvent} generation).
     * @param component current component.
     * @param next next component.
     * @param frame frame (used in {@link FocusEvent} generation).
     */
    private void moveToNextTabFocusableComponent(Context context, Component component, Component next, Frame frame) {
        if (component != null) {
            component.setFocused(false);
            EventProcessor.getInstance().pushEvent(new FocusEvent<>(component, context, frame, next, false));
        }
        if (next != null) {
            Component focusedGui = context.getFocusedGui();
            if (focusedGui != null && focusedGui != component) {
                EventProcessor.getInstance().pushEvent(new FocusEvent<>(focusedGui, context, frame, next, false));
                focusedGui.setFocused(false);
            }
            next.setFocused(true);
            EventProcessor.getInstance().pushEvent(new FocusEvent<>(next, context, frame, next, true));
            context.setFocusedGui(next);
        }
    }
}
