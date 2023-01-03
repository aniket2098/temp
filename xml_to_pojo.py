import xml.etree.ElementTree as ET

def generate_java_class(xml_string):
  # Parse the XML string into an ElementTree object
  root = ET.fromstring(xml_string)

  # The class name is the root element's tag
  class_name = root.tag

  # Initialize the list of fields for the class
  fields = []

  # Recursively process the XML element and its children
  def process_element(element, class_name, fields, depth=0):
    # Iterate over the element's children
    for child in element:
      # If the child has text, it is a leaf node and should be added as a field
      if child.text is not None:
        # Determine the field type based on the element's text
        if child.text.lower() == "true" or child.text.lower() == "false":
          field_type = "boolean"
        elif child.text.isdigit():
          field_type = "int"
        else:
          field_type = "String"

        # Add the field to the list
        fields.append((child.tag, field_type))
      else:
        # The child is an inner element, so create a new class for it and recurse
        inner_class_name = child.tag
        inner_fields = []
        process_element(child, inner_class_name, inner_fields, depth+1)
        fields.append((inner_class_name, inner_class_name))

    # Add fields for the element's attributes
    for key, value in element.items():
      # Determine the field type based on the attribute's value
      if value.lower() == "true" or value.lower() == "false":
        field_type = "boolean"
      elif value.isdigit():
        field_type = "int"
      else:
        field_type = "String"

      # Add the field to the list
      fields.append((key, field_type))

  # Process the root element
  process_element(root, class_name, fields)

  # Generate the Java class
  class_string = "import javax.xml.bind.annotation.XmlRootElement;\n"
  class_string += f"@XmlRootElement(name = \"{class_name}\")\n"
  class_string += f"public class {class_name} {{\n"
  for field in fields:
    class_string += f"  private {field[1]} {field[0]};\n"
  class_string += "}\n"

  return class_string

xml_string = "<xmlObject><element1>abc</element1><element2><element2_1></element2_1><element2_2 name=\"\" value=\"\"/></element2><element3>true</element3></xmlObject>"
java_class = generate_java_class(xml_string)
print(java_class)
