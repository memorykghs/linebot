package pres.linebot.linebot.business.dto;

public class WeatherDataResponse {
	private Records records;

    public static class Records {
        private Location[] locations;

        public static class Location {
            private String locationName;
            private WeatherElement[] weatherElements;


            public static class WeatherElement {
                private String elementName;
                private String description;
                private ElementValue[] elementValues;

                public String getElementName() {
                    return elementName;
                }

                public void setElementName(String elementName) {
                    this.elementName = elementName;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public ElementValue[] getElementValues() {
                    return elementValues;
                }

                public void setElementValues(ElementValue[] elementValues) {
                    this.elementValues = elementValues;
                }

                public static class ElementValue {
                    private String value;
                    private String measures;

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }

                    public String getMeasures() {
                        return measures;
                    }

                    public void setMeasures(String measures) {
                        this.measures = measures;
                    }
                }
            }
        }
    }
}
