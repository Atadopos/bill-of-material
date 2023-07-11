#!/bin/bash

#
# Copyright (c) 2023.  Adib S. A.
# Copyright (c) 2023.  PT Atadopos
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.
#
#

# Build the Docker image
docker build -t my-image .

# Run the container
container_id=$(docker run -d my-image)

# Copy the .deb files from the container to your local system
docker cp ${container_id}:/dist/*.deb .

# Stop and remove the container
docker rm -f ${container_id}