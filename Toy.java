public class Toy {
    // класс нужен только для помещения объектов этого типа в ArrayList
    private String id;
    private String name;
    private Integer count;
    private Double weight;

    public Toy() {
        this.id = setId();
        this.name = setName();
        this.count = setCount();
        this.weight = setWeight();
    }

    private String setId() {
        String id = "";
        while (id == "") {
            System.out.print("Введите id: ");
            id = Main.in.nextLine();
            if (Shop.toyExistsInShop(id)) {
                Main.errorCodes(1, id);
                id = "";
            }
        }
        return id;
    }

    public String getId() {
        return id;
    }

    private String setName() {
        System.out.print("Введите название: ");
        return Main.in.nextLine();
    }

    public String getName() {
        return name;
    }

    private Integer setCount() {
        Integer count = 0;
        while (count <= 0) {
            System.out.print("Введите количество: ");
            String tempCount = Main.in.nextLine();
            try {
                count = Integer.parseInt(tempCount);
            } catch (NumberFormatException e) {
                Main.errorCodes(2, tempCount);
            }
            if (count < 0)
                Main.errorCodes(2, Integer.toString(count));
        }
        return count;
    }

    public Integer getCount() {
        return count;
    }

    private Double setWeight() {
        Double weight = 0.0;
        while (weight <= 0.0) {
            System.out.print("Введите вес: ");
            String tempWeight = Main.in.nextLine();
            try {
                weight = Double.parseDouble(tempWeight);
            } catch (NumberFormatException e) {
                Main.errorCodes(3, tempWeight);
            }
            if (weight < 0)
                Main.errorCodes(3, Double.toString(weight));
            else if ((Shop.getSummaryWeight() + weight) > 100) {
                Main.errorCodes(4, Double.toString(weight));
                weight = 0.0;
            } else
                Shop.increaseSummaryWeight(weight);
        }
        return weight;
    }

    public void changeWeight() {
        Double oldWeight = this.weight;
        Double newWeight = 0.0;
        while (newWeight <= 0.0) {
            System.out.print("Введите новый вес: ");
            String tempWeight = Main.in.nextLine();
            try {
                newWeight = Double.parseDouble(tempWeight);
            } catch (NumberFormatException e) {
                Main.errorCodes(3, tempWeight);
            }
            if (newWeight < 0)
                Main.errorCodes(3, Double.toString(newWeight));
            else if ((Shop.getSummaryWeight() - oldWeight + newWeight) > 100) {
                Main.errorCodes(4, Double.toString(newWeight));
                newWeight = 0.0;
            } else {
                this.weight = newWeight;
                Shop.increaseSummaryWeight(newWeight - oldWeight);
            }
        }
    }

    public Double getWeight() {
        return weight;
    }

    public String toString() {
        return "id: " + id + ", Название: " + name + ", Количество: " + count + ", Вес: " + weight;
    }

    public void decreaseCount() {
        count--;
    }

}
