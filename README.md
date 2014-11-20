![SaltNPepper project](./md/img/SaltNPepper_logo2010.png)
# pepperModules-GenericXMLModules
This project provides a plugin for the linguistic converter framework Pepper (see https://github.com/korpling/pepper). Pepper is a pluggable framework to convert a variety of linguistic formats (like [TigerXML](http://www.ims.uni-stuttgart.de/forschung/ressourcen/werkzeuge/TIGERSearch/doc/html/TigerXML.html), the [EXMARaLDA format](http://www.exmaralda.org/), [PAULA](http://www.sfb632.uni-potsdam.de/paula.html) etc.) into each other. Furthermore Pepper uses Salt (see https://github.com/korpling/salt), the graph-based meta model for linguistic data, which acts as an intermediate model to reduce the number of mappings to be implemented. That means converting data from a format _A_ to format _B_ consists of two steps. First the data is mapped from format _A_ to Salt and second from Salt to format _B_. This detour reduces the number of Pepper modules from _n<sup>2</sup>-n_ (in the case of a direct mapping) to _2n_ to handle a number of n formats.

![n:n mappings via SaltNPepper](./md/img/puzzle.png)

In Pepper there are three different types of modules:
* importers (to map a format _A_ to a Salt model)
* manipulators (to map a Salt model to a Salt model, e.g. to add additional annotations, to rename things to merge data etc.)
* exporters (to map a Salt model to a format _B_).

For a simple Pepper workflow you need at least one importer and one exporter.

This project provides an importer to import data coming from a wide range of xml formats to Salt. A detailed description of that mapping can be found in section [GenericXMLImporter](#details).

## Requirements
Since the here provided module is a plugin for Pepper, you need an instance of the Pepper framework. If you do not already have a running Pepper instance, click on the link below and download the latest stable version (not a SNAPSHOT):

> Note:
> Pepper is a Java based program, therefore you need to have at least Java 7 (JRE or JDK) on your system. You can download Java from https://www.oracle.com/java/index.html or http://openjdk.java.net/ .


## Install module
If this Pepper module is not yet contained in your Pepper distribution, you can easily install it. Just open a command line and enter one of the following program calls:

### Windows

    pepperStart.bat is https://korpling.german.hu-berlin.de/saltnpepper/repository/repo/de/hu_berlin/german/korpling/saltnpepper/pepperModules/pepperModules-GenericXMLModules/1.1.2/de.hu_berlin.german.korpling.saltnpepper.pepperModules.pepperModules-GenericXMLModules_1.1.2.zip

### Linux/Unix

    bash pepperStart.sh is https://korpling.german.hu-berlin.de/saltnpepper/repository/repo/de/hu_berlin/german/korpling/saltnpepper/pepperModules/pepperModules-GenericXMLModules/1.1.2/de.hu_berlin.german.korpling.saltnpepper.pepperModules.pepperModules-GenericXMLModules_1.1.2.zip


## Usage
To use this module in your Pepper workflow, put the following lines into the workflow description file. Note the fixed order of xml elements in the workflow description file: &lt;importer/>, &lt;manipulator/>, &lt;exporter>. The GenericXMLImporter is an importer module, which can be addressed by one of the following alternatives.
A detailed description of the Pepper workflow can be found on the [Pepper project site](https://github.com/korpling/pepper). 

### a) Identify the module by name

```xml
<importer name="GenericXMLImporter" path="PATH_TO_CORPUS"/>
```

### b) Identify the module by formats
```xml
<importer formatName="xml" formatVersion="1.0" path="PATH_TO_CORPUS"/>
```

### c) Use properties
```xml
<importer name="GenericXMLImporter" path="PATH_TO_CORPUS">
  <property key="PROPERTY_NAME">PROPERTY_VALUE</key>
</importer>
```

## Contribute
Since this Pepper module is under a free license, please feel free to fork it from github and improve the module. If you even think that others can benefit from your improvements, don't hesitate to make a pull request, so that your changes can be merged.
If you have found any bugs, or have some feature request, please open an issue on github. If you need any help, please write an e-mail to saltnpepper@lists.hu-berlin.de .

## Funders
This project has been funded by the [department of corpus linguistics and morphology](https://www.linguistik.hu-berlin.de/institut/professuren/korpuslinguistik/) of Humboldt-Universität zu Berlin, [Georgetown University](http://www.georgetown.edu/), [KOMeT](http://korpling.german.hu-berlin.de/komet/) and the [Sonderforschungsbereich 632](https://www.sfb632.uni-potsdam.de/en/). 

## License
  Copyright 2014 Humboldt-Universität zu Berlin.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.



# TEIImporter

The TEIImporter imports data coming from [TEI-XML](http://www.tei-c.org/Guidelines/P5/) files to a [Salt](https://github.com/korpling/salt) model. This importer provides a wide range of customization possibilities via the here described set of properties. Before we talk about the possibility of customizing the mapping, we describe the general and default mapping from TEI to a Salt model.

### Mapping to Salt

The fact that TEI is a XML-format results in the decision to primarily
use tree-like structures that conserve hierarchies. There are two important
exceptions to this: Tokens and the unary "break"
elements like \<lb\> and \<pb\> (these cannot be mapped like this
because their semantic does not fit into XML hierarchie).
For these spans are used. Tokens in TEI can be defined and interpreted in many
different ways and thus customization through properties deals with the
problems occuring because of this.

### Metadata
A metadata key in Salt like
> /fileDesc/publicationStmt/pubPlace

can only be used once. If for some reason (e.g. by using a property)
a key is used for a second time, the TEIImporter will ignore the second
usage and give a warning.

### Properties

Because TEI is a very complex format the behavior of the TEIImporter
depends to a great extent on the properties that the user can use to
customize the behaviour of the TEIImporter. The following table contains an
overview of all usable properties to customize the behaviour of the
TEIImporter. The following section contains a close description to each
single property and describes the resulting differences in the mapping
to the Salt model.

| Name of property                              | Type of property | optional/mandatory | default value      |
|-----------------------------------------------|------------------|--------------------|--------------------|
| [TEIImporter.annotation.default.remove](#adr)         | Boolean          | optional           | false              |
| TEIImporter.annotation.element.rename         | String           | optional           |                    |
| TEIImporter.annotation.namespace              | Boolean          | optional           | false              |
| TEIImporter.annotation.token.span             | Boolean          | optional           | false              |
| TEIImporter.annotation.value.rename           | String           | optional           |                    |
| TEIImporter.element.foreign.token        		| Boolean          | optional           | true               |
| TEIImporter.element.generic.attribute         | Boolean          | optional           | false              |
| TEIImporter.element.generic.node              | String           | optional           | struct             |
| TEIImporter.element.surplus.remove	        | Boolean          | optional           | true               |
| TEIImporter.element.unclear.token        		| Boolean          | optional           | true               |
| TEIImporter.metadata.lastpartonly             | Boolean          | optional           | false              |
| TEIImporter.metadata.redundant.remove         | Boolean          | optional           | false	             |
| TEIImporter.metadata.remove                   | Boolean          | optional           | false              |
| TEIImporter.metadata.remove.list              | String           | optional           |                    |
| TEIImporter.metadata.rename                   | String           | optional           |                    |
| TEIImporter.token.tokenization.defaulttag     | Boolean          | optional           | false              |
| TEIImporter.token.tokenization.sub            | Boolean          | optional           | true               |
| TEIImporter.token.tokenize                  	| Boolean          | optional           | false              |
| TEIImporter.token.tokenize.lang               | String           | optional           | en	             |

<a name="adr"></a>
### TEIImporter.annotation.default.remove 

By default there is an annotation added to each SNode to indicate which element
is responsible for this SNode. This flag disables adding these annotations.

### TEIImporter.annotation.element.rename

A large number of annotations in Salt comes from the element names existing in TEI.
To be able to differentiate, e.g. two struct coming first from <p> and second
from <phr>, a generic annotation is used. The default is to use the element-name.
The tag.rename flag allows customization for the key of such an annotation.
The following format has to be met: 
> tag.rename = pb:PNAME;graphic:Grafikname;phr:Phrase

### TEIImporter.annotation.namespace

To differentiate annotations with the same name, it is possible to add the
namespace coming from TEI to annotations. Example:
> ```<a attr="good"> text </a> <b attr="good"> text </b>```

Here enabling this flag would add the namespaces "a" and "b" to the "attr=good"
annotations.

### TEIImporter.annotation.token.span

By enabling this flag annotations for tokens are additionally imported as spans.

### TEIImporter.annotation.value.rename

The values.rename flag is very similiar to tag.rename, beside here the name of
the value of the annotation can be customized in this case. The format is:
> values.rename = pb:PBVALUE;graphic:GrafikAnnotationValue;phr:PhraseValue

### TEIImporter.element.foreign.token

Does \<foreign\> exclusively create one token annotated as "foreign"? If
this is set "true" (default), then text in \<foreign\> will appear as a
token.

### TEIImporter.element.generic.attribute

By default attributes to elements without nongeneric handling are ignored. To add
these attributes enable this flag.

### TEIImporter.element.generic.node

By default elements without a nongeneric handling in the importer are added
as hierarchical nodes. You can also import them as spans or ignore them.
> generic.node = span

> generic.node = false

Values different than "struct", "span" or "false" will make the importer ignore
elements without a nongeneric handling as well.

### TEIImporter.element.surplus.remove

Will text from \<surplus\> appear in Salt? If this is set "true"
(default), then \<surplus\>-text will be removed.

### TEIImporter.element.unclear.token

Does \<unclear\> exclusively create one token annotated as "unclear"? If
this is set "true" (default), thentext in \<unclear\> will appear as a
token.

### TEIImporter.metadata.lastpartonly

Enabling this flag triggers the deletion of everything from metadata keys but what is
after the last '/'. '@' characters are also removed.

### TEIImporter.metadata.redundant.remove

When handling metadata, the TEIImporter uses default mappings(reference…)
and mappings set by the user. This flag decides whether more than one
SMetaAnnotation can contain the same information when metadata mappings are
used. If set true, redudant metadata will be deleted.

### TEIImporter.metadata.remove

This flag enables the mechanism to exclude certain metadata defined by the keys in
ExcludeMetadataList.

### TEIImporter.metadata.remove.list

List of keys of metadata to be omitted. Keys have to be separated by ";", e.g.:
> ExcludeMetadataList = bibl;date;/fileDesc/publicationStmt/pubPlace

### TEIImporter.metadata.rename

In addition to(or even replacing) the default metadata key mappings, the user is
able to set his own metadata key mappings with this flag. The following example
illustrates this:
> mapping.rename = /fileDesc/publicationStmt/pubPlace:Ort

### TEIImporter.token.tokenization.defaulttag

The user declares that there is one and only one element responsible for
mapping tokens to Salt. Default is \<w\>. In this case SubTokenization should be disabled, otherwise unexpected behaviour may occur.

### TEIImporter.token.tokenization.sub

In this default scenarion, the smallest units of text between elements will be imported as tokens everywhere.
This option should only be disabled if you can guarantee that there is no text outside of the <w>-element or
if you can get over losing parts of the primary text.

### TEIImporter.token.tokenize

Do you want the tokenizer to tokenize text? This option is useful, if
your TEI document contains sections of text that are not tokenized. Using the tokenizer will slow the processing by a considerable amout of time.

### TEIImporter.token.tokenize.lang

The tokenizer currently has support for four languages: English, German,
Italian, French. To choose a language, use the respective ISO 639-1
language code(en, de, it, fr). If no value or a non-supported value is
set, the tokenizer will default to English.
